package seedu.ezdo.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.ezdo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ezdo.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.ezdo.model.todo.DueDate.MESSAGE_DUEDATE_CONSTRAINTS;
import static seedu.ezdo.model.todo.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.ezdo.model.todo.Priority.MESSAGE_PRIORITY_CONSTRAINTS;
import static seedu.ezdo.model.todo.StartDate.MESSAGE_STARTDATE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.ezdo.commons.core.Config;
import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.commons.events.ui.ShowHelpRequestEvent;
import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.ClearCommand;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.CommandResult;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.HelpCommand;
import seedu.ezdo.logic.commands.KillCommand;
import seedu.ezdo.logic.commands.ListCommand;
import seedu.ezdo.logic.commands.QuitCommand;
import seedu.ezdo.logic.commands.SaveCommand;
import seedu.ezdo.logic.commands.SelectCommand;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.Model;
import seedu.ezdo.model.ModelManager;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyEzDo latestSavedEzDo;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(EzDoChangedEvent ezce) {
        latestSavedEzDo = new EzDo(ezce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        Config config = new Config();
        String tempEzDoFile = saveFolder.getRoot().getPath() + "TempEzDo.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempEzDoFile, tempPreferencesFile, config), new UserPrefs());
        EventsCenter.getInstance().registerHandler(this);

        latestSavedEzDo = new EzDo(model.getEzDo()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet

        // sort by a field other than name so that it does not affect the tests
        model.sortTasks(UniqueTaskList.SortCriteria.PRIORITY, true);
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'ezDo' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyEzDo, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyEzDo expectedEzDo,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedEzDo, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'ezDo' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyEzDo, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        EzDo expectedEzDo = new EzDo(model.getEzDo());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedEzDo, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal ezDo data are same as those in the {@code expectedEzDo} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedEzDo} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyEzDo expectedEzDo,
                                       List<? extends ReadOnlyTask> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedEzDo, model.getEzDo());
        assertEquals(expectedEzDo, latestSavedEzDo);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new EzDo(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_quit() {
        assertCommandSuccess("quit", QuitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new EzDo(), Collections.emptyList());
    }

    @Test
    public void execute_quitShortCommand() {
        assertCommandSuccess("q", QuitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new EzDo(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new EzDo(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        assertCommandFailure("add Valid Name p/1"
                + ", todo d/s23/03/2017 12:23", MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/1 "
                + "s/abcd d/24/04/2017 21:11", MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/1 "
                + "s/abcd d/24/04/2017 10:14", MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/1 "
                + "s/12/12/2013 23:00 d/dueDA1E", MESSAGE_DUEDATE_CONSTRAINTS);
    }

    @Test
    public void execute_add_invalidPersonData() {
        assertCommandFailure("add []\\[;] p/3 s/30/03/1999 12:00 d/31/05/1999 13:00 t/invalidName",
                MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/not_numbers s/01/08/1998 14:22 d/11/08/1998 15:21 t/invalidPriority",
                MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/2 s/Invalid_Start.Date d/11/08/1998 12:22 t/invalidStartDate",
                MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/1 s/01/08/1998 14:55 d/invalid_DueDate. t/invalidDueDate",
                MESSAGE_DUEDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/1 s/01/01/1990 02:33 d/01/03/1990 23:35 t/invalid_-[.tag",
                MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.test();
        EzDo expectedEZ = new EzDo();
        expectedEZ.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedEZ,
                expectedEZ.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.test();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal ezDo

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        EzDo expectedEZ = helper.generateEzDo(2);
        List<? extends ReadOnlyTask> expectedList = expectedEZ.getTaskList();

        // prepare ezDo state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedEZ,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord , expectedMessage); //index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new EzDo());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        EzDo expectedEZ = helper.generateEzDo(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedEZ,
                expectedEZ.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

  //@@author A0139248X
    @Test
    public void execute_killInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, KillCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("kill", expectedMessage);
    }

    @Test
    public void execute_killIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("kill");
    }

    @Test
    public void execute_kill_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        EzDo expectedEZ = helper.generateEzDo(threeTasks);
        ArrayList<ReadOnlyTask> tasksToKill = new ArrayList<ReadOnlyTask>();
        tasksToKill.add(threeTasks.get(1));
        expectedEZ.removeTasks(tasksToKill);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("kill 2",
                String.format(KillCommand.MESSAGE_KILL_TASK_SUCCESS, tasksToKill),
                expectedEZ,
                expectedEZ.getTaskList());
    }
  //@@author A0139248X

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        EzDo expectedEZ = helper.generateEzDo(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedEZ,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        EzDo expectedEZ = helper.generateEzDo(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedEZ,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key bla");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        EzDo expectedEZ = helper.generateEzDo(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find bla",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedEZ,
                expectedList);
    }

  //@@author A0139248X
    @Test
    public void execute_save_successful() {
        String directory = "./";

        assertCommandSuccess("save " + directory, String.format(SaveCommand.MESSAGE_SAVE_TASK_SUCCESS,
                directory + SaveCommand.DATA_FILE_NAME), new EzDo(), Collections.emptyList());
    }
  //@@author
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        private Task test() throws Exception {
            Name name = new Name("LogicManager Test Case");
            Priority privatePriority = new Priority("1");
            TaskDate privateStartDate = new StartDate("3/3/2015 21:12");
            TaskDate privateDueDate = new DueDate("tomorrow");
            Recur privateRecur = new Recur("");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, privatePriority, privateStartDate, privateDueDate, privateRecur, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        private Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("LogicManager Generated Task " + seed),
                    new Priority("1"),
                    new StartDate("01/01/2000 09:09"),
                    new DueDate("07/07/2017  12:12"),
                    new Recur(""),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        private String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" p/").append(p.getPriority());
            cmd.append(" s/").append(p.getStartDate());
            cmd.append(" d/").append(p.getDueDate());

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an EzDo with auto-generated tasks.
         */
        private EzDo generateEzDo(int numGenerated) throws Exception {
            EzDo ezDo = new EzDo();
            addToEzDo(ezDo, numGenerated);
            return ezDo;
        }

        /**
         * Generates an EzDo based on the list of Tasks given.
         */
        private EzDo generateEzDo(List<Task> tasks) throws Exception {
            EzDo ezDo = new EzDo();
            addToEzDo(ezDo, tasks);
            return ezDo;
        }

        /**
         * Adds auto-generated Task objects to the given EzDo
         * @param ezDo The EzDo to which the Tasks will be added
         */
        private void addToEzDo(EzDo ezDo, int numGenerated) throws Exception {
            addToEzDo(ezDo, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given EzDo
         */
        private void addToEzDo(EzDo ezDo, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                ezDo.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        private void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        private void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        private List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        private List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        private Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Priority("1"),
                    new StartDate("01/01/2009 09:14"),
                    new DueDate("09/09/2017 10:10"),
                    new Recur(""),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
