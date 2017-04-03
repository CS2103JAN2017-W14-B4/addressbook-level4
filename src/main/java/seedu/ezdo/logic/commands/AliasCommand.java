//@@author A0138907W
package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;
import seedu.ezdo.logic.commands.exceptions.CommandException;

/**
 * Aliases a command to a shortcut string of the user's choice.
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Maps a command to the given shortcut. "
                                               + "Parameters: COMMAND SHORTCUT\n"
                                               + "Example: " + COMMAND_WORD
                                               + " kill z";
    public static final String MESSAGE_ADD_SUCCESS = "Successfully aliased command";
    public static final String MESSAGE_RESET_SUCCESS = "Successfully reset aliases";
    public static final String MESSAGE_ALIAS_ALREADY_IN_USE = "The alias you specified is reserved for other commands";
    public static final String MESSAGE_COMMAND_DOES_NOT_EXIST = "The command you specified does not exist";

    private String command;
    private String alias;
    private boolean hasResetIntention;

    /**
     * Creates an AliasCommand using raw values.
     */
    public AliasCommand(String command, String alias, boolean hasResetIntention) {
        this.command = command;
        this.alias = alias;
        this.hasResetIntention = hasResetIntention;
    }

    /**
     * Executes the alias command.
     */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        if (hasResetIntention) {
            model.getUserPrefs().clearCommandAliases();
            return new CommandResult(MESSAGE_RESET_SUCCESS);
        } else {
            try {
                model.getUserPrefs().addCommandAlias(command, alias);
                return new CommandResult(MESSAGE_ADD_SUCCESS);
            } catch (AliasAlreadyInUseException aaiue) {
                throw new CommandException(MESSAGE_ALIAS_ALREADY_IN_USE);
            } catch (CommandDoesNotExistException cdnee) {
                throw new CommandException(MESSAGE_COMMAND_DOES_NOT_EXIST);
            }
        }
    }

}
