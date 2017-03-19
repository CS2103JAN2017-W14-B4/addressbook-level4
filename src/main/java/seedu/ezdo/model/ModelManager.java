package seedu.ezdo.model;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the ezDo data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    public static final int STACK_CAPACITY = 5;

    private final EzDo ezDo;
    private final FilteredList<ReadOnlyTask> filteredTasks;

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
        filteredTasks = new FilteredList<>(this.ezDo.getTaskList());
        undoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
        redoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
        updateFilteredListToShowAll();
    }

    public ModelManager() {
        this(new EzDo(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyEzDo newData) {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
        ezDo.resetData(newData);
        indicateEzDoChanged();
    }

    @Override
    public ReadOnlyEzDo getEzDo() {
        return ezDo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateEzDoChanged() {
        raise(new EzDoChangedEvent(ezDo));
    }

    @Override
    public synchronized void killTask(ReadOnlyTask target) throws TaskNotFoundException {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
        ezDo.removeTask(target);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
        ezDo.addTask(task);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public synchronized void doneTask(Task doneTask) throws TaskNotFoundException {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
        ezDo.doneTask(doneTask);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
        int ezDoIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        ezDo.updateTask(ezDoIndex, editedTask);
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

    //=========== Filtered Task List Accessors =============================================================

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
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

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        @Override
        String toString();
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

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        @Override
        String toString();
    }

    private class DoneQualifier implements Qualifier {

        DoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getDone();
        }

        @Override
        public String toString() {
            return "";
        }

    }

    private class NotDoneQualifier implements Qualifier {

        NotDoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getDone();
        }

        @Override
        public String toString() {
            return "";
        }

    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent()
                    && !task.getDone();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
