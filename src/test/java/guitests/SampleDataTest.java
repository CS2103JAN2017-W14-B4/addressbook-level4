package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.util.SampleDataUtil;
import seedu.ezdo.testutil.TestUtil;
//@@author A0139248X
public class SampleDataTest extends EzDoGuiTest {
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
}
