//@@author A0138907W
package seedu.ezdo.logic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;
import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.AliasCommand;
import seedu.ezdo.logic.commands.ClearCommand;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.logic.commands.EditCommand;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.HelpCommand;
import seedu.ezdo.logic.commands.KillCommand;
import seedu.ezdo.logic.commands.ListCommand;
import seedu.ezdo.logic.commands.QuitCommand;
import seedu.ezdo.logic.commands.RedoCommand;
import seedu.ezdo.logic.commands.SaveCommand;
import seedu.ezdo.logic.commands.SelectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.logic.commands.UndoCommand;

/**
 * Keeps track of the command aliases specified by user.
 */
public class CommandAliases implements Serializable {

    private static final String[] EXISTING_COMMAND_WORDS = {AddCommand.COMMAND_WORD, AddCommand.SHORT_COMMAND_WORD,
                                                            ClearCommand.COMMAND_WORD, ClearCommand.SHORT_COMMAND_WORD,
                                                            DoneCommand.COMMAND_WORD, DoneCommand.SHORT_COMMAND_WORD,
                                                            EditCommand.COMMAND_WORD, EditCommand.SHORT_COMMAND_WORD,
                                                            FindCommand.COMMAND_WORD, FindCommand.SHORT_COMMAND_WORD,
                                                            HelpCommand.COMMAND_WORD, HelpCommand.SHORT_COMMAND_WORD,
                                                            KillCommand.COMMAND_WORD, KillCommand.SHORT_COMMAND_WORD,
                                                            ListCommand.COMMAND_WORD, ListCommand.SHORT_COMMAND_WORD,
                                                            QuitCommand.COMMAND_WORD, QuitCommand.SHORT_COMMAND_WORD,
                                                            RedoCommand.COMMAND_WORD, RedoCommand.SHORT_COMMAND_WORD,
                                                            SortCommand.COMMAND_WORD, SortCommand.SHORT_COMMAND_WORD,
                                                            UndoCommand.COMMAND_WORD, UndoCommand.SHORT_COMMAND_WORD,
                                                            AliasCommand.COMMAND_WORD,
                                                            SaveCommand.COMMAND_WORD,
                                                            SelectCommand.COMMAND_WORD};

    private HashMap<String, String> commandAliasesMap;

    public CommandAliases() {
        commandAliasesMap = new HashMap<>();
    }

    /**
     * Adds a new alias for a command.
     *
     * @param command An existing command in ezDo to be aliased.
     * @param alias   The new alias for the given command.
     * @throws AliasAlreadyInUseException   If the alias is already in use by an existing ezDo command.
     * @throws CommandDoesNotExistException If the command to be aliased does not exist in ezDo.
     */
    public void addAlias(String command, String alias) throws AliasAlreadyInUseException, CommandDoesNotExistException {
        if (Arrays.asList(EXISTING_COMMAND_WORDS).contains(alias)) {
            throw new AliasAlreadyInUseException();
        }
        if (!Arrays.asList(EXISTING_COMMAND_WORDS).contains(command)) {
            throw new CommandDoesNotExistException();
        }
        commandAliasesMap.put(alias, command);
    }

    /**
     * Clears all existing aliases.
     */
    public void clearAliases() {
        commandAliasesMap = new HashMap<>();
    }

    /**
     * Checks if the given string is an alias for a command in ezDo.
     */
    public boolean checkIfAlias(String alias) {
        return commandAliasesMap.get(alias) != null;
    }

    /**
     * Gets the command that is aliased by the given alias.
     */
    public String getCommandFromAlias(String alias) {
        return commandAliasesMap.get(alias);
    }

}
