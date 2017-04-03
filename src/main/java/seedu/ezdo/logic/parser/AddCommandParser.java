package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STARTDATE, PREFIX_DUEDATE, PREFIX_RECUR, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    getOptionalValue(argsTokenizer, PREFIX_PRIORITY),
                    getOptionalValue(argsTokenizer, PREFIX_STARTDATE),
                    getOptionalValue(argsTokenizer, PREFIX_DUEDATE),
                    getOptionalValue(argsTokenizer, PREFIX_RECUR),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    //@@author A0138907W
    /**
     * Returns the user-provided value associated with the given prefix.
     * If no value was given, an empty String is returned.
     */
    private static String getOptionalValue(ArgumentTokenizer tokenizer, Prefix prefix) {
        if (!tokenizer.getValue(prefix).isPresent()) {
            return "";
        } else {
            return tokenizer.getValue(prefix).get();
        }
    }

}
