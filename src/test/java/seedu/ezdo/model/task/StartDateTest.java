package seedu.ezdo.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.todo.StartDate;

public class StartDateTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidStartDate() {
        // invalid dates
        assertFalse(StartDate.isValidTaskDate(" ")); // spaces only
        assertFalse(StartDate.isValidTaskDate("next")); // non-numeric
        assertFalse(StartDate.isValidTaskDate("13 12 1999")); // spaces within
                                                              // digits
        assertFalse(StartDate.isValidTaskDate("05.10.1977")); // fullstops
                                                              // within digits
        assertFalse(StartDate.isValidTaskDate("22/11/q2r1")); // alphabets
                                                              // within digits

        // valid dates
        assertTrue(StartDate.isValidTaskDate("15/12/1992 10:12")); // month with
                                                                   // 31 days
        assertTrue(StartDate.isValidTaskDate("11/02/2014 07:21")); // month with
                                                                   // 30 days
        assertTrue(StartDate.isValidTaskDate("29/02/2003 20:21")); // leap year
    }
  //@@author A0139248X
    @Test
    public void hashCode_equals() throws Exception {
        StartDate one = new StartDate("05.10.1977");
        assertEquals(one.hashCode(), one.hashCode());
    }

    @Test
    public void invalidDate_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        StartDate date = new StartDate("fgasdf", true);
        date.toString();
    }
}
