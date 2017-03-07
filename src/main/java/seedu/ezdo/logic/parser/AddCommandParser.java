package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STARTDATE, PREFIX_DUEDATE, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).get(),
                    argsTokenizer.getValue(PREFIX_STARTDATE).get(),
                    argsTokenizer.getValue(PREFIX_DUEDATE).get(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
