package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.EditCommand;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.testutil.TaskBuilder;
import seedu.ezdo.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends EzDoGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Alson p/1 s/01/01/2017 12:00 d/08/09/2018 12:00 t/husband";
        int ezDoIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alson").withPriority("1")
                .withStartDate("01/01/2017 12:00").withDueDate("08/09/2018 12:00").withRecur("")
                .withTags("husband").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_shortCommand_success() throws Exception {
        String detailsToEdit = "Alson p/3 s/02/02/2017 12:00 d/10/10/2019 12:00 t/guy";
        int ezDoIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("02/02/2017 12:00").withDueDate("10/10/2019 12:00").withRecur("")
                .withTags("guy").build();

        assertEditSuccess(true, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    //@@author A0138907W
    @Test
    public void edit_eachFieldSpecified_success() throws Exception {

        commandBox.runCommand("edit 1 Alson p/1 s/01/01/2017 12:00 d/08/09/2018 12:00 t/husband");

        int ezDoIndex = 1;

        String detailsToEdit = "p/3";

        TestTask editedTask = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("01/01/2017 12:00").withDueDate("08/09/2018 12:00").withRecur("")
                .withTags("husband").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);

        detailsToEdit = "Alson s/11/11/2017 12:00";

        editedTask = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("11/11/2017 12:00").withDueDate("08/09/2018 12:00").withRecur("")
                .withTags("husband").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);

        detailsToEdit = "Alson d/01/01/2018 12:00";

        editedTask = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("11/11/2017 12:00").withDueDate("01/01/2018 12:00").withRecur("")
                .withTags("husband").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);

        detailsToEdit = "Alson t/brother";

        editedTask = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("11/11/2017 12:00").withDueDate("01/01/2018 12:00").withRecur("")
                .withTags("brother").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    //@@author

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int ezDoIndex = 2;

        TestTask taskToEdit = expectedTasksList[ezDoIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int ezDoIndex = 2;

        TestTask taskToEdit = expectedTasksList[ezDoIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(false, ezDoIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Ellll";
        int filteredTaskListIndex = 1;
        int ezDoIndex = 5;

        TestTask taskToEdit = expectedTasksList[ezDoIndex - 1];

        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Ellll").build();

        assertEditSuccess(false, filteredTaskListIndex, ezDoIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit 1 d/12due");
        assertResultMessage(DueDate.MESSAGE_DUEDATE_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/01/01/2017 00:00 d/01/01/2016 00:00");
        assertResultMessage(Messages.MESSAGE_TASK_DATES_INVALID);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Alice Pauline p/1 "
                                + "s/12/12/2016 11:22 d/14/03/2017 22:33 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param usesShortCommand whether to use the short or long version of the command
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param ezDoIndex index of task to edit in ezDo.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(boolean usesShortCommand, int filteredTaskListIndex, int ezDoIndex,
                                    String detailsToEdit, TestTask editedTask) {
        if (usesShortCommand) {
            commandBox.runCommand("e " + filteredTaskListIndex + " " + detailsToEdit);
        } else {
            commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        }

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[ezDoIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
