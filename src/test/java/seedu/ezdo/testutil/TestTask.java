package seedu.ezdo.testutil;

import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.TaskDate;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private static final String PREFIX_PRIORITY = "p/";
    private static final String PREFIX_STARTDATE = "s/";
    private static final String PREFIX_DUEDATE = "d/";

    private Name name;
    private TaskDate startDate;
    private TaskDate dueDate;
    private Priority priority;
    private Recur recur;
    private boolean done;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.startDate = taskToCopy.getStartDate();
        this.dueDate = taskToCopy.getDueDate();
        this.recur = taskToCopy.getRecur();
        this.done = taskToCopy.getDone();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public void setDueDate(TaskDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDone() {
        this.done = true;
    }

    public void setRecur(Recur recur) {
        this.recur = recur;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskDate getDueDate() {
        return dueDate;
    }

    @Override
    public Recur getRecur() {
        return recur;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0138907W
    /**
     * Constructs an add command for the test task.
     * @param usesShortCommand Specifies if the long or short version of the command should be used. ("add" or "a")
     * @return This is the add command that will add the test task.
     */
    public String getAddCommand(boolean usesShortCommand) {
        StringBuilder sb = new StringBuilder();
        if (usesShortCommand) {
            sb.append("a " + this.getName().fullName + " ");
        } else {
            sb.append("add " + this.getName().fullName + " ");
        }
        sb.append(PREFIX_STARTDATE + this.getStartDate().value + " ");
        sb.append(PREFIX_DUEDATE + this.getDueDate().value + " ");
        sb.append(PREFIX_PRIORITY + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
