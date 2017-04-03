//@@author A0138907W
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TypicalTestTasks;

public class SortCommandTest extends EzDoGuiTest {

    @Override
    protected EzDo getInitialData() {
        EzDo ez = new EzDo();
        TypicalTestTasks.loadEzDoWithSampleData(ez);
        try {
            ez.addTask(new Task(td.kappa));
            ez.addTask(new Task(td.leroy));
            ez.addTask(new Task(td.megan));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
        return ez;
    }

    @Test
    public void sortByFields() {
        TestTask[] expectedList;

        // sort by start date
        commandBox.runCommand("sort s");
        expectedList = new TestTask[] {td.elle, td.george, td.daniel, td.carl, td.benson, td.fiona, td.alice, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by start date in descending order
        commandBox.runCommand("s s d");
        expectedList = new TestTask[] {td.alice, td.fiona, td.benson, td.carl, td.daniel, td.george, td.elle, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by due date
        commandBox.runCommand("s d");
        expectedList = new TestTask[] {td.carl, td.elle, td.george, td.daniel, td.fiona, td.benson, td.alice, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by name
        commandBox.runCommand("sort n a");
        expectedList = new TestTask[] {td.alice, td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by priority ascending
        commandBox.runCommand("s p");
        expectedList = new TestTask[] {td.alice, td.carl, td.daniel, td.fiona, td.elle, td.george, td.benson, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // sort by priority descending
        commandBox.runCommand("s p d");
        expectedList = new TestTask[] {td.elle, td.george, td.carl, td.daniel, td.fiona, td.alice, td.benson, td.kappa,
                                       td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        //invalid command
        commandBox.runCommand("sort z");
        assertResultMessage(SortCommand.MESSAGE_INVALID_FIELD);

        //invalid command
        commandBox.runCommand("sort");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void maintainSortOrder() {
        TestTask[] expectedList;

        commandBox.runCommand("sort d");

        // check that Jack is in the right place after adding him
        commandBox.runCommand(td.jack.getAddCommand(false));
        expectedList = new TestTask[] {td.carl, td.elle, td.george, td.jack, td.daniel, td.fiona, td.benson, td.alice,
                                       td.kappa, td.leroy, td.megan};
        assertTrue(taskListPanel.isListMatching(expectedList));

        // check that Carl is in the right place after editing his due date
        commandBox.runCommand("edit 1 d/12/12/2099");
        String expectedName = taskListPanel.getTask(7).getName().toString();
        assertTrue(expectedName.equals(td.carl.getName().toString()));
    }

    //@@author A0139248X
    @Test
    public void sort_invalidOrder() {
        commandBox.runCommand("sort p s");
        assertResultMessage(String.format(SortCommand.MESSAGE_INVALID_ORDER, SortCommand.MESSAGE_USAGE))
        ;
    }
}
