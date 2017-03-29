package seedu.ezdo.model.todo;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.parser.DateParser;

/**
 * Represents the date associated with tasks.
 */
public abstract class TaskDate {

    public String value;

    public static final String TASKDATE_VALIDATION_REGEX = "^$|(0[1-9]|"
            + "[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)[0-9]{2} "
            + "(2[0-3]|[0-1][0-9])[:][0-5][0-9]";
    public static final String FIND_TASKDATE_VALIDATION_REGEX = "^$|(0[1-9]|[12][0-9]|3[01])"
            + "[/](0[1-9]|1[012])[/](19|20)[0-9]{2}";
    public static final String MESSAGE_FIND_DATE_CONSTRAINTS = "Please enter a date in this form: DD/MM/YYYY "
            + "or in the natural language.";

    public TaskDate(String taskDate) {
        DateParser dateParser = new DateParser(taskDate);
        this.value = dateParser.value;
    }
//@@author A0141010L
    public TaskDate(String taskDate, boolean isFind) throws IllegalValueException {
        String trimmedDate = taskDate.trim();
        assert trimmedDate != null;
        DateParser dateParser = new DateParser(taskDate);
        this.value = dateParser.value;
    }
//@@author A0141010L
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                        && this.value.equals(((TaskDate) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidTaskDate(String test) {
        return test.matches(TASKDATE_VALIDATION_REGEX);
    }

}
