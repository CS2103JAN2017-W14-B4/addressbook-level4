package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SaveCommand;

public class SaveCommandParser {

    public Command parse(String args) {
        assert args != null;
        System.out.println("Arguments are:" + args);
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        try {
            return new SaveCommand(argsTokenizer.getPreamble().get());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
    }

}
