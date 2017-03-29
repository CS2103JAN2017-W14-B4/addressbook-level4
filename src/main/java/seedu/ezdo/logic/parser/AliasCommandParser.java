package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ezdo.logic.commands.AliasCommand;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;


/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser {

    private static final int COMMAND_INDEX = 0;
    private static final int ALIAS_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     */
    public Command parse(String args) {
        String[] commandArguments = ParserUtil.parseCommandAlias(args);
        if (commandArguments.length == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        String command = commandArguments[COMMAND_INDEX];
        String alias = commandArguments[ALIAS_INDEX];
        return new AliasCommand(command, alias);
    }

}
