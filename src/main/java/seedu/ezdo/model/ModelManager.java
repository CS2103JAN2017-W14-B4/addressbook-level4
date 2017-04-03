package seedu.ezdo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.model.IsSortedAscendingChangedEvent;
import seedu.ezdo.commons.events.model.SortCriteriaChangedEvent;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.commons.util.DateUtil;
import seedu.ezdo.commons.util.SearchParameters;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the ezDo data. All changes to any model
 * should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    public static final int STACK_CAPACITY = 5;

    private final EzDo ezDo;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final UserPrefs userPrefs;
  //@@author A0139248X
    private SortCriteria currentSortCriteria;
    private Boolean currentIsSortedAscending;

    private final FixedStack<ReadOnlyEzDo> undoStack;
    private final FixedStack<ReadOnlyEzDo> redoStack;

    /**
     * Initializes a ModelManager with the given ezDo and userPrefs.
     */
    public ModelManager(ReadOnlyEzDo ezDo, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(ezDo, userPrefs);

        logger.fine("Initializing with ezDo: " + ezDo + " and user prefs " + userPrefs);

        this.ezDo = new EzDo(ezDo);
        this.userPrefs = userPrefs;
        filteredTasks = new FilteredList<>(this.ezDo.getTaskList());
        currentSortCriteria = userPrefs.getSortCriteria();
        currentIsSortedAscending = userPrefs.getIsSortedAscending();
        undoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
        redoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
        updateFilteredListToShowAll();
    }

    public ModelManager() {
        this(new EzDo(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyEzDo newData) {
        updateStacks();
        ezDo.resetData(newData);
        indicateEzDoChanged();
    }
  //@@author
    @Override
    public ReadOnlyEzDo getEzDo() {
        return ezDo;
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateEzDoChanged() {
        raise(new EzDoChangedEvent(ezDo));
    }
  //@@author A0139248X
    @Override
    public synchronized void killTasks(ArrayList<ReadOnlyTask> tasksToKill) throws TaskNotFoundException {
        updateStacks();
        ezDo.removeTasks(tasksToKill);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, DateException {
        checkTaskDate(task);
        updateStacks();
        ezDo.addTask(task);
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public synchronized boolean toggleTasksDone(ArrayList<Task> toggleTasks) {
        updateStacks();
        ezDo.toggleTasksDone(toggleTasks);
        if (toggleTasks.get(0).getDone()) {
            updateFilteredListToShowAll();
        } else {
            updateFilteredDoneList();
        }
        indicateEzDoChanged();
        return toggleTasks.get(0).getDone();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, DateException {
        assert editedTask != null;
        checkTaskDate(editedTask);
        updateStacks();
        int ezDoIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        ezDo.updateTask(ezDoIndex, editedTask);
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        indicateEzDoChanged();
    }

    @Override
    public void undo() throws EmptyStackException {
        ReadOnlyEzDo currentState = new EzDo(this.getEzDo());
        ReadOnlyEzDo prevState = undoStack.pop();
        ezDo.resetData(prevState);
        redoStack.push(currentState);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public void redo() throws EmptyStackException {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        ezDo.resetData(redoStack.pop());
        undoStack.push(prevState);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public void updateStacks() throws EmptyStackException {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
    }

    @Override
    public void checkTaskDate(ReadOnlyTask task) throws DateException {
        assert task != null;
        try {
            if (!DateUtil.isTaskDateValid(task)) {
                throw new DateException("Start date after due date!");
            }
        } catch (ParseException pe) {
            logger.info("Parse exception while checking if task date valid");
            throw new DateException("Error parsing dates!");
        }
    }
  //@@author
    // =========== Filtered Task List Accessors
    // =============================================================
  //@@author A0141010L
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(SearchParameters searchParameters) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(searchParameters)));
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredTaskList(new PredicateExpression(new NotDoneQualifier()));
    }

    @Override
    public void updateFilteredDoneList() {
        updateFilteredTaskList(new PredicateExpression(new DoneQualifier()));
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
    }

    private class DoneQualifier implements Qualifier {

        DoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getDone();
        }
    }

    private class NotDoneQualifier implements Qualifier {

        NotDoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getDone();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private Optional<Priority> priority;
        private Optional<TaskDate> startDate;
        private Optional<TaskDate> dueDate;
        private Set<String> tags;
        private boolean startBefore;
        private boolean dueBefore;
        private boolean startAfter;
        private boolean dueAfter;

        NameQualifier(SearchParameters searchParameters) {

            this.nameKeyWords = searchParameters.getNames();
            this.priority = searchParameters.getPriority();
            this.startDate = searchParameters.getStartDate();
            this.dueDate = searchParameters.getDueDate();
            this.tags = searchParameters.getTags();
            this.startBefore = searchParameters.getStartBefore();
            this.dueBefore = searchParameters.getdueBefore();
            this.startAfter = searchParameters.getStartAfter();
            this.dueAfter = searchParameters.getDueAfter();

        }

        @Override
        public boolean run(ReadOnlyTask task) {

            Set<String> taskTagStringSet = convertToTagStringSet(task.getTags().toSet());

            return (nameKeyWords.contains("") || nameKeyWords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)))
                    && !task.getDone()
                    && comparePriority(task.getPriority())
                    && (((!startBefore && !startAfter) && compareStartDate(task.getStartDate()))
                            || (startBefore && compareBeforeStart(task.getStartDate()))
                            || (startAfter && compareAfterStart(task.getStartDate())))
                    && (((!dueBefore && !dueAfter) && compareDueDate(task.getDueDate()))
                            || (dueBefore && compareBeforeDue(task.getDueDate()))
                            || (dueAfter && compareAfterDue(task.getDueDate())))
                    && (taskTagStringSet.containsAll(tags));

        }

        private Set<String> convertToTagStringSet(Set<Tag> tags) {
            Object[] tagArray = tags.toArray();
            Set<String> tagSet = new HashSet<String>();

            for (int i = 0; i < tags.size(); i++) {
                tagSet.add(((Tag) tagArray[i]).tagName);
            }

            return tagSet;
        }

        private boolean comparePriority(Priority taskPriority) {

            String taskPriorityString = taskPriority.toString();
            boolean priorityExist = (taskPriorityString.length() != 0);

            return (!priority.isPresent() || (priority.get().toString().equals("") && priorityExist)
                    || (priorityExist && taskPriorityString.equals(priority.get().toString())));
        }

        private boolean compareStartDate(TaskDate taskStartDate) {

            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);

            return (!startDate.isPresent() || (startDate.get().toString().equals("") && taskStartDateExist)
                    || (taskStartDateExist && taskStartDateString.substring(0, 10).equals
                       (startDate.get().toString().substring(0, 10))));
        }

        private boolean compareDueDate(TaskDate taskDueDate) {

            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);

            return (!dueDate.isPresent() || (dueDate.get().toString().equals("") && taskDueDateExist)
                    || (taskDueDateExist && taskDueDateString.substring(0, 10).equals
                       (dueDate.get().toString().substring(0, 10))));
        }

        private boolean compareBeforeStart(TaskDate taskStartDate) {
            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);

            return (!startDate.isPresent() || (startDate.get().toString().equals("") && taskStartDateExist)
                    || (taskStartDateExist && comesBefore(startDate.get().toString(), taskStartDateString)));
        }

        private boolean compareBeforeDue(TaskDate taskDueDate) {
            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);

            return (!dueDate.isPresent() || (dueDate.get().toString().equals("") && taskDueDateExist)
                    || (taskDueDateExist && comesBefore(dueDate.get().toString(), taskDueDateString)));
        }

        private boolean compareAfterStart(TaskDate taskStartDate) {
            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);

            return (!startDate.isPresent() || (startDate.get().toString().equals("") && taskStartDateExist)
                    || (taskStartDateExist && comesBefore(taskStartDateString, startDate.get().toString())));
        }

        private boolean compareAfterDue(TaskDate taskDueDate) {
            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);

            return (!dueDate.isPresent() || (dueDate.get().toString().equals("") && taskDueDateExist)
                    || (taskDueDateExist && comesBefore(taskDueDateString, dueDate.get().toString())));
        }

        private boolean comesBefore(String givenDate, String taskDate) {

            int givenDD = Integer.parseInt(givenDate.substring(0, 2));
            int givenMM = Integer.parseInt(givenDate.substring(3, 5));
            int givenYYYY = Integer.parseInt(givenDate.substring(6, 10));

            int taskDD = Integer.parseInt(taskDate.substring(0, 2));
            int taskMM = Integer.parseInt(taskDate.substring(3, 5));
            int taskYYYY = Integer.parseInt(taskDate.substring(6, 10));

            return (taskYYYY < givenYYYY)
                   || ((taskYYYY == givenYYYY) && (taskMM < givenMM))
                   || ((taskYYYY == givenYYYY) && (taskMM == givenMM) && (taskDD <= givenDD));

        }

    }

    //@@author A0138907W
    /**
     * Sorts the task in ezDo by the given sort criteria.
     *
     * @param sortCriteria      The field to sort by.
     * @param isSortedAscending If true, sorts in ascending order. Otherwise, sorts in descending order.
     */
    @Override
    public void sortTasks(SortCriteria sortCriteria, Boolean isSortedAscending) {
        if (!this.currentSortCriteria.equals(sortCriteria)) {
            this.currentSortCriteria = sortCriteria;
            indicateSortCriteriaChanged();
        }
        if (!this.currentIsSortedAscending.equals(isSortedAscending)) {
            this.currentIsSortedAscending = isSortedAscending;
            indicateIsSortedAscendingChanged();
        }
        ezDo.sortTasks(sortCriteria, isSortedAscending);
        indicateEzDoChanged();
    }

  //@@author
  //@@author A0139248X

    public void indicateSortCriteriaChanged() {
        raise(new SortCriteriaChangedEvent(currentSortCriteria));
    }

    //@@author A0138907W
    /**
     * Raises a {@code IsSortedAscendingChangedEvent}.
     */
    public void indicateIsSortedAscendingChanged() {
        raise(new IsSortedAscendingChangedEvent(currentIsSortedAscending));
    }
}
