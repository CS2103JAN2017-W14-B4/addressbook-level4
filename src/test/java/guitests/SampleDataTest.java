package guitests;

import static org.junit.Assert.assertNotNull;
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
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.util.SampleDataUtil;
import seedu.ezdo.testutil.TestUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SampleDataUtil.class)
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
        PowerMockito.mock(Task.class);
        PowerMockito.whenNew(Task.class).withAnyArguments().thenThrow(new IllegalValueException("invalid value"));
        thrown.expect(AssertionError.class);
        SampleDataUtil.getSampleTasks();
    }

    @Test
    public void ezDo_duplicateData_getSampleEzDo() throws Exception {
        PowerMockito.mock(EzDo.class);
        PowerMockito.whenNew(EzDo.class).withAnyArguments().thenThrow(new DuplicateTaskException());
        thrown.expect(AssertionError.class);
        SampleDataUtil.getSampleEzDo();
    }

    @Test
    public void getSampleEzDo_notNull() {
        ReadOnlyEzDo sampleEzDo = SampleDataUtil.getSampleEzDo();
        assertNotNull(sampleEzDo);
    }
}
