package seedu.ezdo.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//@@author A0139248X
public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : EzDo\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/ezDo.xml\n" +
                "EzDo name : ezDo";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

    @Test
    public void equalsMethod_notConfig() {
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(new Integer(1)));
    }

    @Test
    public void hashCodeMethod_equals() {
        Config defaultConfig = new Config();
        assertEquals(defaultConfig.hashCode(), defaultConfig.hashCode());
    }

    @Test
    public void getEzDoNameMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertEquals(defaultConfig.getEzDoName(), "ezDo");
    }

}
