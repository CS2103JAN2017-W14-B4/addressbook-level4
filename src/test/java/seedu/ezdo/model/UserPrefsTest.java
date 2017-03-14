package seedu.ezdo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

public class UserPrefsTest {

    private UserPrefs first = new UserPrefs();
    private UserPrefs second = new UserPrefs();

    @Test
    public void bothEquals_assertTrue() {
        assertTrue(first.equals(second));
    }

    @Test
    public void nullEquals_assertFalse() {
        second = null;
        assertFalse(first.equals(second));
    }

    @Test
    public void hash() {
        assertEquals(first.hashCode(), Objects.hash(first.getGuiSettings()));
    }
}
