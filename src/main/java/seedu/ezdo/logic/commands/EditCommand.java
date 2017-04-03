package seedu.ezdo.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 * Edits the details of an existing task in ezDo.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String SHORT_COMMAND_WORD = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [p/PRIORITY] [s/START_DATE] [d/DUE_DATE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/2 s/10/12/2017 d/15/12/2017";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in ezDo.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }
    //@@author A0139248X-reused
    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (DateException de) {
            throw new CommandException(Messages.MESSAGE_TASK_DATES_INVALID);
        }

        model.updateFilteredListToShowAll();
        lastShownList = model.getFilteredTaskList();
        int index = lastShownList.lastIndexOf(editedTask);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
    //@@author
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        TaskDate updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(taskToEdit::getStartDate);
        TaskDate updatedDueDate = editTaskDescriptor.getDueDate().orElseGet(taskToEdit::getDueDate);
        Recur updatedRecur = editTaskDescriptor.getRecur().orElseGet(taskToEdit::getRecur);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedPriority, updatedStartDate, updatedDueDate, updatedRecur, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<TaskDate> startDate = Optional.empty();
        private Optional<TaskDate> dueDate = Optional.empty();
        private Optional<Recur> recur = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.priority = toCopy.getPriority();
            this.startDate = toCopy.getStartDate();
            this.dueDate = toCopy.getDueDate();
            this.recur = toCopy.getRecur();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.priority,
                    this.startDate, this.dueDate, this.recur, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setStartDate(Optional<TaskDate> startDate) {
            assert startDate != null;
            this.startDate = startDate;
        }

        public Optional<TaskDate> getStartDate() {
            return startDate;
        }

        public void setDueDate(Optional<TaskDate> dueDate) {
            assert dueDate != null;
            this.dueDate = dueDate;
        }

        public Optional<TaskDate> getDueDate() {
            return dueDate;
        }

        public void setRecur(Optional<Recur> recur) {
            assert recur != null;
            this.recur = recur;
        }

        public Optional<Recur> getRecur() {
            return recur;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
