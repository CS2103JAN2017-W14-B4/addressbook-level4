package seedu.ezdo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.DueDate;

public class DueDateTest {

    @Test
    public void isValidDueDate() {
        // invalid dates
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // spaces only
        assertFalse(DueDate.isValidDueDate("priority")); // non-numeric
        assertFalse(DueDate.isValidDueDate("23 04 1995")); // spaces within digits
        assertFalse(DueDate.isValidDueDate("15.12.1945")); // fullstops within digits
        assertFalse(DueDate.isValidDueDate("20/01/p041")); // alphabets within digits

        // valid dates
        assertTrue(DueDate.isValidDueDate("31/12/1993")); // month with 31 days
        assertTrue(DueDate.isValidDueDate("30/04/2016")); // month with 30 days
        assertTrue(DueDate.isValidDueDate("29/02/2016")); // leap year
    }
}
