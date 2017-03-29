package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.logic.commands.IncorrectCommand;
//@@author A0141010L

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     */
    public Command parse(String args) {

        if (("").equals(args)) {
            return new DoneCommand();
        }

        ArrayList<Integer> indexes = ParserUtil.parseIndexes(args);
        if (indexes.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(indexes);
    }

}
