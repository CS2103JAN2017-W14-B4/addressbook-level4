package seedu.ezdo.model.todo;

import seedu.ezdo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in ezDo.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Priority getPriority();
    StartDate getStartDate();
    DueDate getDueDate();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getStartDate().equals(this.getStartDate()))
                && other.getDueDate().equals(this.getDueDate());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getPriority())
                .append(" StartDate: ")
                .append(getStartDate())
                .append(" DueDate: ")
                .append(getDueDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
