package seedu.ezdo.model.todo;

import seedu.ezdo.commons.exceptions.IllegalValueException;


/**
 * Represents the start date of a task.
 */
public class StartDate extends TaskDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Start dates should at least be in the format DD/MM/YYYY HH:MM (24 hours)";


    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        super(startDate);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
    }
//@@author A0141010L
    /**
     * Validates given start date when user finds by start date
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startDate, boolean isFind) throws IllegalValueException {
        super(startDate, isFind);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_FIND_DATE_CONSTRAINTS);
        }
    }

}
