package seedu.ezdo.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author A0139248X
public class GuiSettingsTest {

    @Test
    public void equals_same_true() {
        GuiSettings setting = new GuiSettings();
        assertTrue(setting.equals(setting));
    }

    @Test
    public void equals_oneNull_false() {
        GuiSettings setting = new GuiSettings();
        assertFalse(setting.equals(null));
    }
}
