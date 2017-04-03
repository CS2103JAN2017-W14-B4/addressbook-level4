package guitests;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.logic.commands.SaveCommand;
//@@author A0139248X
@RunWith(JMockit.class)
public class SaveCommandTest extends EzDoGuiTest {

    private final String validDirectory = "./";
    private final String inexistentDirectory = "data/COWABUNGA";

    @Test
    public void save_validDirectory_success() {
        commandBox.runCommand("save " + validDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SAVE_TASK_SUCCESS,
                validDirectory + SaveCommand.DATA_FILE_NAME));
    }

    @Test
    public void save_invalidFormat_failure() {
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void save_inexistentDirectory_failure() {
        commandBox.runCommand("save " + inexistentDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_DIRECTORY_PATH_DOES_NOT_EXIST));
    }

    @Test
    public void save_validDirectory_noAdminPermissions_failure() throws Exception {
        new MockUp<FileUtil>() {
            @Mock
            public void createIfMissing(File file) throws IOException {
                throw new IOException();
            }
        };
        commandBox.runCommand("save " + validDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_DIRECTORY_PATH_INVALID));
    }
}
