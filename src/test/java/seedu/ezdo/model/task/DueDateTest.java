package seedu.ezdo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.todo.DueDate;

public class DueDateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDueDate() {
        // invalid dates
        assertFalse(DueDate.isValidTaskDate(" ")); // spaces only
        assertFalse(DueDate.isValidTaskDate("priority")); // non-numeric
        assertFalse(DueDate.isValidTaskDate("23 04 1995")); // spaces within digits
        assertFalse(DueDate.isValidTaskDate("15.12.1945")); // fullstops within digits
        assertFalse(DueDate.isValidTaskDate("20/01/p041")); // alphabets within digits

        // valid dates
        assertTrue(DueDate.isValidTaskDate("31/12/1993 20:02")); // month with 31 days
        assertTrue(DueDate.isValidTaskDate("30/04/2016 11:11")); // month with 30 days
        assertTrue(DueDate.isValidTaskDate("29/02/2016 00:01")); // leap year
    }

    @Test
    public void invalidDate_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        DueDate one = new DueDate("asdf", true);
    }
}

