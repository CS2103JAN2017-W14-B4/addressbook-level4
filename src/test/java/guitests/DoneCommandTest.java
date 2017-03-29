package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.commons.core.Messages.MESSAGE_WRONG_LIST;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
//@@author A0141010L
public class DoneCommandTest extends EzDoGuiTest {
    @Test
    public void done_success() {
        //marks first task in the list as done
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] doneList = td.getTypicalDoneTasks();
        int targetIndex = 1;
        TestTask toDone = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);

        //marks the middle task in the list as done
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        targetIndex = currentList.length / 2;
        toDone = currentList[targetIndex - 1];
        assertDoneSuccess(true, targetIndex, currentList, doneList);

        //marks last task in the list as done
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        targetIndex = currentList.length;
        toDone = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);

        //invalid index
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid.");

        //invalid command
        commandBox.runCommand("done a");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));

        //invalid command
        commandBox.runCommand("dones 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //view done tasks
        commandBox.runCommand("done");
        assertTrue(taskListPanel.isListMatching(doneList));

        //invalid input when viewing done task
        commandBox.runCommand("done " + doneList.length);
        assertResultMessage(MESSAGE_WRONG_LIST);

    }

    private void assertDoneSuccess(boolean usesShortCommand, int targetIndexOneIndexed, final TestTask[] currentList,
                                   final TestTask[] doneList) {

        TestTask taskToDone = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        ArrayList<TestTask> tasksToDone = new ArrayList<TestTask>();
        tasksToDone.add(taskToDone);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] expectedDone = TestUtil.addTasksToList(doneList, taskToDone);

        if (usesShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("done " + targetIndexOneIndexed);
        }

        //confirm the list now contains all done tasks including the one just marked as done
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksToDone));

        //confirm the new done list contains the right data
        commandBox.runCommand("done");
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToDone.getName().fullName);
        assertMatching(taskToDone, addedCard);
        assertTrue(taskListPanel.isListMatching(expectedDone));

        //confirm the undone list does not contain the task just marked as done
        commandBox.runCommand("list");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

    }

}
