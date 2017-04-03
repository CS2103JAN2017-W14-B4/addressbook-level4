package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.testutil.TestTask;
//@@author A0141010L
public class FindCommandTest extends EzDoGuiTest {

    @Test
    public void find_nonEmptyList() {

        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results
        assertFindResult("find p/1", td.alice);
        assertFindResult("find s/11/11/2015", td.benson);
        assertFindResult("find s/before 30/12/2012", td.daniel, td.elle, td.george);
        assertFindResult("find s/after 01/12/2015", td.alice, td.fiona);
        assertFindResult("find d/14/04/2016", td.daniel);
        assertFindResult("find d/before 30/12/2014", td.carl);
        assertFindResult("find d/after 30/12/2016", td.alice, td.benson);
        assertFindResult("find t/owesMoney", td.benson);
        assertFindResult("find Meier p/2", td.daniel);
        assertFindResult("find Meier s/11/11/2015", td.benson);
        assertFindResult("find Meier s/11th Nov 2015", td.benson);
        assertFindResult("find Meier s/Nov 11th 2015", td.benson);
        assertFindResult("find Meier s/11-11-2015 d/12/02/2017 t/owesMoney t/friends", td.benson);
        assertFindResult("find p/2 d/april 14th 2016", td.daniel);
        assertFindResult("find p/2 d/14/04/2016", td.daniel);
        assertFindResult("find p/1", td.alice);

        //find after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("kill 1");
        assertFindResult("find Meier", td.benson, td.daniel);
    }

    //@@author A0138907W
    @Test
    public void find_shortCommand() {
        assertFindResult("f Mark"); // no results
        assertFindResult("f Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("kill 1");
        assertFindResult("f Meier", td.daniel);
    }

    //@@author
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail_1() {
        commandBox.runCommand("find");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("find s/10a");
        assertResultMessage("0 tasks listed!");
        commandBox.runCommand("find Meier p/1 s/11-11-2015 d/02/12/2017 t/owesMoney t/nonExistentTag");
        assertResultMessage("0 tasks listed!");
    }

    @Test
    public void find_invalidCommand_fail_2() {
        commandBox.runCommand("find s/asdasd");
        assertResultMessage(TaskDate.MESSAGE_FIND_DATE_CONSTRAINTS);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
