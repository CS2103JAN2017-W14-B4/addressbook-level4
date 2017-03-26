package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.testutil.TestTask;

public class FindCommandTest extends EzDoGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results
        assertFindResult("find Meier p/1", td.benson);
        assertFindResult("find Meier p/1 s/11/11/2015", td.benson);
        assertFindResult("find p/2 d/14/04/2016", td.daniel);
        assertFindResult("find p/1", td.alice, td.benson);
        assertFindResult("find t/owesMoney", td.benson);

        //find after deleting one result
        commandBox.runCommand("kill 1");
        assertFindResult("find Meier", td.daniel);
    }

    @Test
    public void find_shortCommand() {
        assertFindResult("f Mark"); // no results
        assertFindResult("f Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("kill 1");
        assertFindResult("f Meier", td.daniel);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("find s/10a");
        assertResultMessage("Please enter a date in this form: DD/MM/YYYY");
        commandBox.runCommand("find d/10a");
        assertResultMessage("Please enter a date in this form: DD/MM/YYYY");
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
