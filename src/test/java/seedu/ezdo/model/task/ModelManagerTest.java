package seedu.ezdo.model.task;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.util.DateUtil.compareDateStrings;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.util.DateUtil;
import seedu.ezdo.model.ModelManager;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.testutil.TaskBuilder;
//@@author A0139248X
@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtil.class)
public class ModelManagerTest {

    private ModelManager modelManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        modelManager = new ModelManager();
    }

    @Test
    public void checkDate_parseFail() throws Exception {
        ReadOnlyTask task = new TaskBuilder().build();
        PowerMockito.mockStatic(DateUtil.class);
        BDDMockito.given(DateUtil.isTaskDateValid(task))
                .willThrow(new ParseException("parse fail", 0));
        thrown.expect(DateException.class);
        modelManager.checkTaskDate(task);
    }
  //@@author

    //@@author A0138907W
    @Test
    public void compareDate_parseFail() {
        String dateStringOne = "12345";
        String dateStringTwo = "11/11/2016 12:34";

        boolean parsingFailed;
        try {
            compareDateStrings(dateStringOne, dateStringTwo, true);
            parsingFailed = false;
        } catch (AssertionError ae) {
            parsingFailed =  true;
        }

        assertTrue(parsingFailed);
    }

}
