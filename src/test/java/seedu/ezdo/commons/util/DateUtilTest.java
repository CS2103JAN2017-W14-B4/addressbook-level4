package seedu.ezdo.commons.util;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.testutil.TaskBuilder;
import seedu.ezdo.testutil.TestTask;

//@@author A0139248X
@RunWith(JMockit.class)
public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void dateFormat_parseException() throws Exception {
        thrown.expect(AssertionError.class);
        new MockUp<SimpleDateFormat>()
        {
            @Mock
            Date parse(String date) throws ParseException
            {
                throw new ParseException("parse exception", 1);
            }
        };
        String dateString1 = "omg";
        String dateString2 = "asdf";
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

    //@@author A0139248X
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
