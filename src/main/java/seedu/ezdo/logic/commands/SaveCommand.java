package seedu.ezdo.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.logic.commands.exceptions.CommandException;
//@@author A0139248X
/**
 * Changes the save location of ezDo.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of ezDo. "
            + "Parameters: DIRECTORYPATH \n"
            + "Example: " + COMMAND_WORD
            + " C:/Users/Tom/Desktop";

    public static final String DATA_FILE_NAME = "/ezDo.xml";
    public static final String MESSAGE_DIRECTORY_PATH_DOES_NOT_EXIST = "The directory path given does not exist.";
    public static final String MESSAGE_DIRECTORY_PATH_INVALID = "The directory path is invalid or"
            + " you do not have administrative permissions.";
    public static final String MESSAGE_SAVE_TASK_SUCCESS = "New Save Location: %1$s";

    private final String directoryPath;

    /**
     * Creates a SaveCommand using raw values.
     * @throws IllegalValueException if the directory path is invalid
     */
    public SaveCommand(String path) throws IllegalValueException {
        assert path != null;
        File directory = new File(path);
        if (!FileUtil.isDirectoryExists(directory)) {
            throw new IllegalValueException(MESSAGE_DIRECTORY_PATH_DOES_NOT_EXIST);
        }
        directoryPath = path + DATA_FILE_NAME;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert directoryPath != null;
        try {
            File file = new File(directoryPath);
            FileUtil.createIfMissing(file);
            EventsCenter.getInstance().post(new EzDoDirectoryChangedEvent(directoryPath));
            return new CommandResult(String.format(MESSAGE_SAVE_TASK_SUCCESS, directoryPath));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_DIRECTORY_PATH_INVALID);
        }
    }
}
