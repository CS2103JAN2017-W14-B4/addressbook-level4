package seedu.ezdo.testutil;

import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.StartDate;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private static final String PREFIX_EMAIL = "e/";
    private static final String PREFIX_PRIORITY = "p/";
    private static final String PREFIX_STARTDATE = "s/";
    private static final String PREFIX_DUEDATE = "d/";

    private Name name;
    private StartDate startDate;
    private DueDate dueDate;
    private Priority priority;
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
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append(PREFIX_STARTDATE + this.getStartDate().value + " ");
        sb.append(PREFIX_DUEDATE + this.getDueDate().value + " ");
        sb.append(PREFIX_PRIORITY + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
