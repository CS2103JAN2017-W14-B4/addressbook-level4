package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
<<<<<<< HEAD
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;
=======
import static seedu.ezdo.logic.parser.CliSyntax.*;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e

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
<<<<<<< HEAD
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
=======
                new ArgumentTokenizer(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STARTDATE, PREFIX_TAG);
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    Integer.parseInt(argsTokenizer.getValue(PREFIX_PRIORITY).get()),
                    argsTokenizer.getValue(PREFIX_EMAIL).get(),
                    argsTokenizer.getValue(PREFIX_STARTDATE).get(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}