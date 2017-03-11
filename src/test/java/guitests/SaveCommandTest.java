package guitests;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.ezdo.logic.commands.SaveCommand;

public class SaveCommandTest extends EzDoGuiTest {

    private final String validDirectory = "data/";
    private final String inexistentDirectory = "data/COWABUNGA";
/*
    @Test
    public void save_validDirectory_success() {
       commandBox.runCommand("save " + validDirectory);
      assertResultMessage(String.format(SaveCommand.MESSAGE_SAVE_TASK_SUCCESS, validDirectory));
    }
*/
    @Test
    public void save_invalidFormat_failure() {
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void save_invalidDirectory_failure() {
        commandBox.runCommand("save " + inexistentDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_DIRECTORY_PATH_DOES_NOT_EXIST));
    }

}
