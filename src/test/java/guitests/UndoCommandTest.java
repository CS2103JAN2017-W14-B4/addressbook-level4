package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.UndoCommand;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
//@@author A0139248X
public class UndoCommandTest extends EzDoGuiTest {

    @Test
    public void undo_noPrev() {
        //undo without anything to undo
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    @Test
    public void undo_invalidCommand() {
        //invalid command
        commandBox.runCommand("undoo");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void undo_add() {
        //undo an add
        TestTask taskToAdd = td.hoon;
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(taskToAdd.getAddCommand(false));
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_clear() {
        //undo a clear
        TestTask[]currentList = td.getTypicalTasks();
        commandBox.runCommand("clear");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_twoThings() {
        //undo two things
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand(false));
        TestTask[] currentListTwo = TestUtil.addTasksToList(currentList, taskToAdd);
        commandBox.runCommand("clear");
        assertUndoSuccess(currentListTwo);
        assertUndoSuccess(currentList);
    }

    private void assertUndoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
