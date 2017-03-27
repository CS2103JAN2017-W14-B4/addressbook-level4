//@@author A0138907W
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.ezdo.commons.exceptions.InvalidSortOrderException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements CommandParser {

    private static final String NAME_SORT_KEYWORD = "n";
    private static final String PRIORITY_SORT_KEYWORD = "p";
    private static final String START_DATE_SORT_KEYWORD = "s";
    private static final String DUE_DATE_SORT_KEYWORD = "d";

    private static final String ASCENDING_KEYWORD = "a";
    private static final String DESCENDING_KEYWORD = "d";

    private static final int SORT_CRITERIA_INDEX = 0;
    private static final int SORT_ORDER_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String[]> sortArugmentsField = ParserUtil.parseSortCriteria(args);
        if (!sortArugmentsField.isPresent()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] sortArguments = sortArugmentsField.get();
        String sortCriteria = sortArguments[SORT_CRITERIA_INDEX];
        String sortOrder = sortArguments[SORT_ORDER_INDEX];

        Boolean isSortedAscending;

        if (sortOrder == null) {
            isSortedAscending = true;
        } else {
            try {
                isSortedAscending = determineIfIsSortedAscending(sortOrder);
            } catch (InvalidSortOrderException isoe) {
                isSortedAscending = null;
            }
        }

        switch (sortCriteria) {
        case NAME_SORT_KEYWORD:
            return new SortCommand(SortCriteria.NAME, isSortedAscending);
        case PRIORITY_SORT_KEYWORD:
            return new SortCommand(SortCriteria.PRIORITY, isSortedAscending);
        case START_DATE_SORT_KEYWORD:
            return new SortCommand(SortCriteria.START_DATE, isSortedAscending);
        case DUE_DATE_SORT_KEYWORD:
            return new SortCommand(SortCriteria.DUE_DATE, isSortedAscending);
        default:
            return new SortCommand(null, isSortedAscending);
        }
    }

    private boolean determineIfIsSortedAscending(String sortOrder) throws InvalidSortOrderException {
        Boolean isSortedAscending;
        switch(sortOrder) {
        case ASCENDING_KEYWORD:
            isSortedAscending = true;
            break;
        case DESCENDING_KEYWORD:
            isSortedAscending = false;
            break;
        default:
            throw new InvalidSortOrderException();
        }
        return isSortedAscending;
    }

}
