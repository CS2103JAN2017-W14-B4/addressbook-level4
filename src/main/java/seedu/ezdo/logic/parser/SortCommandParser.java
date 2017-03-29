//@@author A0138907W
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements CommandParser {

    private static final String SORT_KEYWORD_NAME = "n";
    private static final String SORT_KEYWORD_PRIORITY = "p";
    private static final String SORT_KEYWORD_START_DATE = "s";
    private static final String SORT_KEYWORD_DUE_DATE = "d";

    private static final String KEYWORD_ASCENDING = "a";
    private static final String KEYWORD_DESCENDING = "d";

    private static final int INDEX_OF_SORT_CRITERIA = 0;
    private static final int INDEX_OF_SORT_ORDER = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        Optional<String[]> sortArgumentsField = ParserUtil.parseSortCriteria(args);
        if (!sortArgumentsField.isPresent()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String[] sortArguments = sortArgumentsField.get();
        String sortCriteriaArgument = sortArguments[INDEX_OF_SORT_CRITERIA];
        String sortOrderArgument = sortArguments[INDEX_OF_SORT_ORDER];

        SortCriteria sortCriteria = getSortCriteria(sortCriteriaArgument);
        Boolean isSortedAscending = checkIfSortedAscending(sortOrderArgument);

        return new SortCommand(sortCriteria, isSortedAscending);
    }

    private SortCriteria getSortCriteria(String sortCriteriaArgument) {
        SortCriteria sortCriteria;
        switch (sortCriteriaArgument) {
        case SORT_KEYWORD_NAME:
            sortCriteria = SortCriteria.NAME;
            break;
        case SORT_KEYWORD_PRIORITY:
            sortCriteria = SortCriteria.PRIORITY;
            break;
        case SORT_KEYWORD_START_DATE:
            sortCriteria = SortCriteria.START_DATE;
            break;
        case SORT_KEYWORD_DUE_DATE:
            sortCriteria = SortCriteria.DUE_DATE;
            break;
        default:
            sortCriteria = null;
            break;
        }
        return sortCriteria;
    }

    private Boolean checkIfSortedAscending(String sortOrderArgument) {
        Boolean isSortedAscending;
        if (sortOrderArgument == null) {
            // If no sort order was specified, then default to ascending sort order.
            isSortedAscending = true;
        } else {
            switch(sortOrderArgument) {
            case KEYWORD_ASCENDING:
                isSortedAscending = true;
                break;
            case KEYWORD_DESCENDING:
                isSortedAscending = false;
                break;
            default:
                isSortedAscending = null;
                break;
            }
        }
        return isSortedAscending;
    }

}
