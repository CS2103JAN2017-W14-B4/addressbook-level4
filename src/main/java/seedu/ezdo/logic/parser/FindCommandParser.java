package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.parser.ArgumentTokenizer.Prefix;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.TaskDate;
//@@author A0141010L
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        assert args != null;

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STARTDATE, PREFIX_DUEDATE,
                PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String namesToMatch = argsTokenizer.getPreamble().orElse("");
        String[] splitNames = namesToMatch.split("\\s+");

        ArrayList<Object> listToCompare = new ArrayList<Object>();
        ArrayList<Boolean> searchIndicatorList = new ArrayList<Boolean>();
        Optional<Priority> findPriority;
        Optional<TaskDate> findStartDate = null;
        Optional<TaskDate> findDueDate = null;
        Set<String> findTags;
        boolean searchBeforeStartDate = false;
        boolean searchBeforeDueDate = false;
        boolean searchAfterStartDate = false;
        boolean searchAfterDueDate = false;

        try {

            boolean isFind = true;
            Optional<String> optionalStartDate = getOptionalValue(argsTokenizer, PREFIX_STARTDATE);
            Optional<String> optionalDueDate = getOptionalValue(argsTokenizer, PREFIX_DUEDATE);

            if (isFindBefore(optionalStartDate)) {
                optionalStartDate = parseFindBefore(optionalStartDate);
                searchBeforeStartDate = true;
            }

            if (isFindBefore(optionalDueDate)) {
                optionalDueDate = parseFindBefore(optionalDueDate);
                searchBeforeDueDate = true;
            }

            if (isFindAfter(optionalStartDate)) {
                optionalStartDate = parseFindAfter(optionalStartDate);
                searchAfterStartDate = true;
            }

            if (isFindAfter(optionalDueDate)) {
                optionalDueDate = parseFindAfter(optionalDueDate);
                searchAfterDueDate = true;
            }

            findStartDate = ParserUtil.parseStartDate(optionalStartDate, isFind);
            findDueDate = ParserUtil.parseDueDate(optionalDueDate, isFind);
            findTags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            findPriority = ParserUtil.parsePriority(getOptionalValue(argsTokenizer, PREFIX_PRIORITY));

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        Set<String> keywords = new HashSet<String>(Arrays.asList(splitNames));
        listToCompare.add(keywords);
        listToCompare.add(findPriority);
        listToCompare.add(findStartDate);
        listToCompare.add(findDueDate);
        listToCompare.add(findTags);
        searchIndicatorList.add(searchBeforeStartDate);
        searchIndicatorList.add(searchBeforeDueDate);
        searchIndicatorList.add(searchAfterStartDate);
        searchIndicatorList.add(searchAfterDueDate);

        return new FindCommand(listToCompare, searchIndicatorList);
    }

    private Optional<String> getOptionalValue(ArgumentTokenizer tokenizer, Prefix prefix) {
        Optional<String> optionalString;
        if (!tokenizer.getValue(prefix).isPresent()) {
            optionalString = Optional.empty();
        } else {
            optionalString = Optional.of(tokenizer.getValue(prefix).get());
        }
        return optionalString;
    }

    private Optional<String> parseFindBefore(Optional<String> taskDate) {
        Optional<String> optionalDate;
        String taskDateString = taskDate.get();
        String commandString = taskDateString.substring(6, taskDateString.length()).trim();
        optionalDate = Optional.of(commandString);
        return optionalDate;
    }

    private Optional<String> parseFindAfter(Optional<String> taskDate) {
        Optional<String> optionalDate;
        String taskDateString = taskDate.get();
        String commandString = taskDateString.substring(5, taskDateString.length()).trim();
        System.out.println(commandString);
        optionalDate = Optional.of(commandString);
        return optionalDate;
    }

    private boolean isFindBefore(Optional<String> taskDate) {
        if (!taskDate.isPresent()) {
            return false;
        } else {
            String taskDateString = taskDate.get();
            if (taskDateString.length() <= 6) {
                return false;
            } else {
                String prefixToCompare = "before";
                String byPrefix = taskDateString.substring(0, 6);
                return byPrefix.equals(prefixToCompare);
            }
        }
    }

    private boolean isFindAfter(Optional<String> taskDate) {
        if (!taskDate.isPresent()) {
            return false;
        } else {
            String taskDateString = taskDate.get();
            if (taskDateString.length() <= 5) {
                return false;
            } else {
                String prefixToCompare = "after";
                String byPrefix = taskDateString.substring(0, 5);
                return byPrefix.equals(prefixToCompare);
            }
        }
    }

}
