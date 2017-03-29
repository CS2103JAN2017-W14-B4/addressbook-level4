package seedu.ezdo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author A0139248X
public class UserPrefsTest {

    @Test
    public void same_equals_true() {
        UserPrefs prefs = new UserPrefs();
        assertTrue(prefs.equals(prefs));
    }

    @Test
    public void notUserPrefs_equals_false() {
        UserPrefs prefs = new UserPrefs();
        assertFalse(prefs.equals(new Integer(1)));
    }

    @Test
    public void same_hashCode() {
        UserPrefs prefs = new UserPrefs();
        assertEquals(prefs.hashCode(), prefs.hashCode());
    }
}
