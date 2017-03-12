package seedu.ezdo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserPrefsTest {

    UserPrefs first = new UserPrefs();
    UserPrefs second = new UserPrefs();

    @Test
    public void bothEquals_assertTrue() {
        assertTrue(first.equals(second));
    }

    @Test
    public void nullEquals_assertFalse() {
        second = null;
        assertFalse(first.equals(second));
    }
}
