package seedu.ezdo.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.util.SampleDataUtil;

//@@author A0139248X
@RunWith(JMockit.class)
public class SampleDataUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSampleTasks_ive_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new Expectations(Name.class) {
            {
                new Name("Buy one cherry fruit"); result = new IllegalValueException("illegal value");
            }
        };
        SampleDataUtil.getSampleTasks();
    }

    @Test
    public void getSampleEzDo_duplicateTask_throwsAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        new Expectations(EzDo.class) {
            {
                new EzDo(); result = new DuplicateTaskException();
            }
        };
        SampleDataUtil.getSampleEzDo();
    }
}
