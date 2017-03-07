package seedu.ezdo.model.todo;


import seedu.ezdo.commons.exceptions.IllegalValueException;


/**
 * Represents the start date of a todo.
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Start dates should be in the format DD-MM-YYYY, and it should not be blank";

    public static final String STARTDATE_VALIDATION_REGEX = "^\\d{1,2}/\\d{1,2}/\\d{4}$";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        this.value = startDate;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
