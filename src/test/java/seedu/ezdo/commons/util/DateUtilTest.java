package seedu.ezdo.commons.util;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.testutil.TaskBuilder;
import seedu.ezdo.testutil.TestTask;
//@@author A0139248X
@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class, SimpleDateFormat.class})
public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void dateFormat_parseException() throws Exception {
        thrown.expect(AssertionError.class);
        SimpleDateFormat mock = mock(SimpleDateFormat.class);
        PowerMockito.whenNew(SimpleDateFormat.class).
        withAnyArguments().thenReturn(mock);
        String dateString1 = "omg";
        String dateString2 = "asdf";
        when(mock.parse(dateString1)).thenThrow(new ParseException("parse exception", 1));
        DateUtil.compareDateStrings(dateString1, dateString2, true);
    }

    @Test
    public void noDate_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("")
                .withDueDate("")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void noStartDate_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("")
                .withDueDate("10/10/2019")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void noDueDate_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("02/02/2017")
                .withDueDate("")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    //@@author A0138907W
    @Test
    public void noDates_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("")
                .withDueDate("")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    //@@author
    @Test
    public void twoDates_equal_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("02/02/2017").withDueDate("02/02/2017")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void startAfterDue_fail() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("02/02/2020").withDueDate("10/10/2019")
                .withTags("guy").build();
        assertFalse(DateUtil.isTaskDateValid(task));
    }
}
