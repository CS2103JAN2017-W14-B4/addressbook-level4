package seedu.ezdo.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.Name;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("     ")); // whitespace only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters

        // valid name (at least 1 alphanumeric character)
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("$1")); // one symbol and one number
        assertTrue(Name.isValidName("$a")); // one symbol and one alphabet
        assertTrue(Name.isValidName("#!@#%!$^!@&#^!*@&#^!&*#^!&^#*!@^#b")); // a lot of symbols and one alphabet
        assertTrue(Name.isValidName("#!@#%!$^!@&#^!*@&#^!&*#^!&^#*!@^#1")); // a lot of symbols and one number

    }

    @Test
    public void hashCode_equals() throws Exception {
        Name one = new Name("omg");
        assertEquals(one.hashCode(), one.hashCode());
    }
}
