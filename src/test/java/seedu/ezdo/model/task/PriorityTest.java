package seedu.ezdo.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.Priority;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priority numbers
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits

        // valid priority numbers
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2")); // short priority numbers
        assertTrue(Priority.isValidPriority("3")); // long priority numbers
    }

    @Test
    public void hashCode_equals() throws Exception {
        Priority one = new Priority("1");
        assertEquals(one.hashCode(), one.hashCode());
    }
}
