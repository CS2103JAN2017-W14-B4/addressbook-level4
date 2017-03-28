package seedu.ezdo.logic.commands;

import java.util.ArrayList;
//@@author A0141010L
/**
 * Finds and lists all tasks in ezDo whose name contains any of the argument
 * keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " buy milk clean p/3";

    private final ArrayList<Object> listToCompare;
    private final boolean searchBeforeStartDate;
    private final boolean searchBeforeDueDate;
    private final boolean searchAfterStartDate;
    private final boolean searchAfterDueDate;

    public FindCommand(ArrayList<Object> listToCompare, boolean searchBeforeStartDate, boolean searchBeforeDueDate,
                       boolean searchAfterStartDate, boolean searchAfterDueDate) {
        this.listToCompare = listToCompare;
        this.searchBeforeStartDate = searchBeforeStartDate;
        this.searchBeforeDueDate = searchBeforeDueDate;
        this.searchAfterStartDate = searchAfterStartDate;
        this.searchAfterDueDate = searchAfterDueDate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(listToCompare, searchBeforeStartDate,
                searchBeforeDueDate, searchAfterStartDate, searchAfterDueDate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
//@@author