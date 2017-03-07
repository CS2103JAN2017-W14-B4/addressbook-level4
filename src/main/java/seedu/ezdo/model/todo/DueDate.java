package seedu.ezdo.model.todo;


import seedu.ezdo.commons.exceptions.IllegalValueException;


/**
 * Represents the due date of a todo.
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "Due dates should be in the format DD/MM/YYYY, and it should not be blank";

    public static final String DUEDATE_VALIDATION_REGEX =
            "([12][0-9]|3[01]|0?[1-9])/(0?[1-9]|1[012])/((?:19|20)\\d\\d)";

    public final String value;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
        assert dueDate != null;
        if (!isValidDueDate(dueDate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value = dueDate;
    }

    /**
     * Returns true if a given string is a valid due date.
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(DUEDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
