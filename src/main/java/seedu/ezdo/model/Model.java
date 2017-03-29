package seedu.ezdo.model;

import java.util.ArrayList;
import java.util.EmptyStackException;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEzDo newData);

    /** Returns the EzDo */
    ReadOnlyEzDo getEzDo();

    /** Returns the UserPrefs */
    UserPrefs getUserPrefs();

    void sortTasks(SortCriteria sortCriteria, Boolean isSortedAscending);
  //@@author A0139248X
    /** Deletes the given task. */
    void killTasks(ArrayList<ReadOnlyTask> tasksToKill) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task. */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, DateException;

    /** Checks the task and makes sure the dates are logical.
     * i.e. start date not after due date.
     * @throws DateException
     */
    void checkTaskDate(ReadOnlyTask task) throws DateException;

    /** Marks a task as done.
     * @throws TaskNotFoundException */
    void doneTasks(ArrayList<Task> tasksToDone);

    /** Undo the previous undoable command
     * @throws EmptyStackException */
    void undo() throws EmptyStackException;

    /** Redo the previous undone command
     * @throws EmptyStackException */
    void redo() throws EmptyStackException;

    /** Update stack when new command is executed
     * @throws EmptyStackException */
    void updateStacks() throws EmptyStackException;
  //@@author A0139248X

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, DateException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by multiple fields*/
    void updateFilteredTaskList(ArrayList<Object> listToCompare, ArrayList<Boolean> searchIndicatorList);

    /** Updates the filter of the filtered task list to show done tasks*/
    void updateFilteredDoneList();

}
