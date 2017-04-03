package seedu.ezdo.logic.commands;

import java.util.ArrayList;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.util.MultipleIndexCommandUtil;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;
//@@author A0139248X
/**
 * Deletes a task identified using its last displayed index from ezDo.
 */
public class KillCommand extends Command {

    public static final String COMMAND_WORD = "kill";
    public static final String SHORT_COMMAND_WORD = "k";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_KILL_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task was not found in list.";

    public final ArrayList<Integer> targetIndexes;
    public final ArrayList<ReadOnlyTask> tasksToKill;

    public KillCommand(ArrayList<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
        tasksToKill = new ArrayList<ReadOnlyTask>();
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (!MultipleIndexCommandUtil.isIndexValid(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int i = 0; i < targetIndexes.size(); i++) {
            ReadOnlyTask taskToKill = lastShownList.get(targetIndexes.get(i) - 1);
            tasksToKill.add(taskToKill);
        }

        try {
            model.killTasks(tasksToKill);
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_KILL_TASK_SUCCESS, tasksToKill));
    }
}
