package seedu.ezdo.model;

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
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.StartDate;
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

   /* @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, new Optional(), new Optional(), new Optional())));
    } */
    
    @Override
    public void updateFilteredTaskList(Set<String> keywords, Optional optionalPriority, Optional optionalStartDate, Optional optionalDueDate, Set<String> findTag) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, optionalPriority, optionalStartDate, optionalDueDate, findTag)));
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
        private Optional<Priority> priority;
        private Optional<StartDate> startDate;
        private Optional<DueDate> dueDate;
        private Set<String> tags;
        
        NameQualifier(Set<String> nameKeyWords, Optional<Priority> priority, Optional<StartDate> startDate, Optional<DueDate> dueDate, Set<String> tags) {
            this.nameKeyWords = nameKeyWords;
            this.priority = priority;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.tags = tags;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String taskStartDate = task.getStartDate().toString();
            String taskDueDate = task.getDueDate().toString();
            Set<String> taskTagStringSet = convertToTagStringSet(task.getTags().toSet());
            
            return (nameKeyWords.contains("") || nameKeyWords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)))
                    && !task.getDone()
                    && (!priority.isPresent() || task.getPriority().toString().equals(priority.get().toString()))
                    && (!startDate.isPresent() || (taskStartDate.length() != 0) && taskStartDate.substring(0, startDate.get().toString().length()).equals(startDate.get().toString()))
                    && (!dueDate.isPresent() || (taskDueDate.length() != 0) && taskDueDate.substring(0, dueDate.get().toString().length()).equals(dueDate.get().toString()))
                    && (taskTagStringSet.containsAll(tags));
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
        
        public Set<String> convertToTagStringSet(Set<Tag> tags) {
            Object[] tagArray = tags.toArray();
            Set<String> tagSet = new HashSet<String>();
            
            for (int i = 0; i < tags.size(); i++) {
                tagSet.add(((Tag)tagArray[i]).tagName);
            }
            
            return tagSet;
        }
    }

}