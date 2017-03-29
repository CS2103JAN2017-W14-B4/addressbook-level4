package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;
import seedu.ezdo.commons.exceptions.IllegalValueException;
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
    public static final String MESSAGE_SUCCESS = "Successfully aliased command";
    public static final String MESSAGE_ALIAS_ALREADY_IN_USE = "The alias you specified is reserved for other commands";
    public static final String MESSAGE_COMMAND_DOES_NOT_EXIST = "The command you specified does not exist";

    private String command;
    private String alias;

    /**
     * Creates an AliasCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AliasCommand(String command, String alias) {
        this.command = command;
        this.alias = alias;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.getUserPrefs().addCommandAlias(command, alias);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (AliasAlreadyInUseException aaiue) {
            throw new CommandException(MESSAGE_ALIAS_ALREADY_IN_USE);
        } catch (CommandDoesNotExistException cdnee) {
            throw new CommandException(MESSAGE_COMMAND_DOES_NOT_EXIST);
        }
    }

}
