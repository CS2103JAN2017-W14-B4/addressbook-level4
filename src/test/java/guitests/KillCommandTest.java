package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.logic.commands.KillCommand.MESSAGE_KILL_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import seedu.ezdo.logic.commands.KillCommand;
import seedu.ezdo.model.ModelManager;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
//@@author A0139248X
public class KillCommandTest extends EzDoGuiTest {

    @Test
    public void kill() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertKillSuccess(false, targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertKillSuccess(false, targetIndex, currentList);

        //delete using the short command
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertKillSuccess(true, targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertKillSuccess(false, targetIndex, currentList);

        //invalid index
        commandBox.runCommand("kill " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid.");
    }

    @Test
    public void kill_taskNotFound_throwsAssertion() {
        new MockUp<ModelManager>()
        {
            @Mock
            void killTasks(ArrayList<ReadOnlyTask> tasksToKill) throws TaskNotFoundException
            {
                throw new TaskNotFoundException();
            }
        };
        commandBox.runCommand("kill 1");
        assertResultMessage(String.format(KillCommand.MESSAGE_TASK_NOT_FOUND));
    }
    /**
     * Runs the kill command to delete the task at specified index and confirms the result is correct.
     * @param usesShortCommand Whether to use the short or long version of the command
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertKillSuccess(boolean usesShortCommand, int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToKill = currentList[targetIndexOneIndexed - 1]; // array uses zero indexing
        ArrayList<ReadOnlyTask> tasksToKill = new ArrayList<ReadOnlyTask>();
        tasksToKill.add(taskToKill);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        if (usesShortCommand) {
            commandBox.runCommand("k " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("kill " + targetIndexOneIndexed);
        }

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_KILL_TASK_SUCCESS, tasksToKill));
    }

}
