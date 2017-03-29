//@@author A0138907W
package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Sorts all tasks in ezDo by their name, start date, due date or priority.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String SHORT_COMMAND_WORD = "s";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks.";
    public static final String MESSAGE_INVALID_FIELD = "You must specify a valid field to sort by (n for name, "
                                                       + "p for priority, s for start date, d for due date)";
    public static final String MESSAGE_INVALID_ORDER = "You must specify a valid order to sort by "
                                                       + "(a for ascending, d for descending)";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts the task by the field specified.\n"
        + "Parameters: FIELD [ORDER]\n"
        + "Example: " + COMMAND_WORD + " d a";

    private SortCriteria sortCriteria;
    private Boolean isSortedAscending;

    public SortCommand(SortCriteria sortCriteria, Boolean isSortedAscending) {
        this.sortCriteria = sortCriteria;
        this.isSortedAscending = isSortedAscending;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (sortCriteria == null) {
            throw new CommandException(MESSAGE_INVALID_FIELD);
        }
        if (isSortedAscending == null) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }
        model.sortTasks(sortCriteria, isSortedAscending);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(0));
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
