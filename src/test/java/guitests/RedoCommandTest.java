package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.RedoCommand;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
//@@author A0139248X
public class RedoCommandTest extends EzDoGuiTest {
    @Test
    public void redo_invalid() {
        //invalid command
        commandBox.runCommand("redoo");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void redo_withoutUndo() {
        //redo without anything to undo
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    @Test
    public void redo_add() {
        //redo an add, after undoing it
        TestTask taskToAdd = td.hoon;
        TestTask[] currentList = td.getTypicalTasks();
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand(false));
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_kill() {
        //redo a kill, after undoing it
        commandBox.runCommand("kill 1");
        TestTask[] currentList = td.getTypicalTasks();
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        int targetIndex = 1;
        commandBox.runCommand("kill " + targetIndex);
    }

    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
}
