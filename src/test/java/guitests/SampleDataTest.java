package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.util.SampleDataUtil;
import seedu.ezdo.testutil.TestUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Task.class)
public class SampleDataTest extends EzDoGuiTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Override
    protected EzDo getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void ezDo_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void invalidSampleData_loadSampleData() throws Exception {
        try {
            PowerMockito.mockStatic(Task.class);
            PowerMockito.whenNew(Task.class).withArguments(new Name("Lol"), new Priority("1"),
                    new StartDate("12/12/2017"), new DueDate("12/12/2017"),
                    new UniqueTagList("test")).thenThrow(new IllegalValueException("invalid sample data"));

            Task[] expectedList = SampleDataUtil.getSampleTasks();
            assertTrue(taskListPanel.isListMatching(expectedList));
        } catch (IllegalValueException ioe) {
            thrown.expect(AssertionError.class);
        }
    }

    @Test
    public void ezDo_duplicateData_getSampleEzDo() throws Exception {
        try {
            PowerMockito.mock(EzDo.class);
            PowerMockito.whenNew(EzDo.class).withNoArguments().thenThrow(new DuplicateTaskException());
            ReadOnlyEzDo ezdo = SampleDataUtil.getSampleEzDo();
        } catch (DuplicateTaskException dte) {
            thrown.expect(AssertionError.class);
        }
    }

}
