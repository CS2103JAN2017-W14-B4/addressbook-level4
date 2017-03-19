package seedu.ezdo.model;

import java.util.EmptyStackException;
import java.util.Set;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEzDo newData);

    /** Returns the EzDo */
    ReadOnlyEzDo getEzDo();

    /** Deletes the given task. */
    void killTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task. */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Marks a task as done.
     * @throws TaskNotFoundException */
    void doneTask(Task task) throws TaskNotFoundException;

    /** Undo the previous undoable command
     * @throws EmptyStackException */
    void undo() throws EmptyStackException;

    /** Redo the previous undone command
     * @throws EmptyStackException */
    void redo() throws EmptyStackException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to show done tasks*/
    void updateFilteredDoneList();

}
