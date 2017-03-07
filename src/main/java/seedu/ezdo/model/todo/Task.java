package seedu.ezdo.model.todo;

import java.util.Objects;

import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.model.tag.UniqueTagList;

/**
 * Represents a Task in ezDo.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private StartDate startDate;
    private DueDate dueDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority priority, StartDate startDate, DueDate dueDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, startDate, tags);
        this.name = name;
        this.priority = priority;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(),
                source.getStartDate(), source.getDueDate(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    public void setDueDate(DueDate dueDate) {
        assert dueDate != null;
        this.dueDate = dueDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setStartDate(replacement.getStartDate());
        this.setDueDate(replacement.getDueDate());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, startDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
