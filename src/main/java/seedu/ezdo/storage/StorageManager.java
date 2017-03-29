package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.Config;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.ConfigUtil;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
//@@author A0139248X
/**
 * Manages storage of EzDo data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private EzDoStorage ezDoStorage;
    private UserPrefsStorage userPrefsStorage;
    private Config config;

    public StorageManager(EzDoStorage ezDoStorage, UserPrefsStorage userPrefsStorage, Config config) {
        super();
        this.config = config;
        this.ezDoStorage = ezDoStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String ezDoFilePath, String userPrefsFilePath, Config config) {
        this(new XmlEzDoStorage(ezDoFilePath), new JsonUserPrefsStorage(userPrefsFilePath), config);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ EzDo methods ==============================

    @Override
    public String getEzDoFilePath() {
        return ezDoStorage.getEzDoFilePath();
    }

    @Override
    public void setEzDoFilePath(String path) {
        ezDoStorage.setEzDoFilePath(path);
    }

    @Override
    public Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException {
        return readEzDo(ezDoStorage.getEzDoFilePath());
    }

    @Override
    public Optional<ReadOnlyEzDo> readEzDo(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return ezDoStorage.readEzDo(filePath);
    }

    @Override
    public void saveEzDo(ReadOnlyEzDo ezDo) throws IOException {
        saveEzDo(ezDo, ezDoStorage.getEzDoFilePath());
    }

    @Override
    public void saveEzDo(ReadOnlyEzDo ezDo, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        ezDoStorage.saveEzDo(ezDo, filePath);
    }

    @Override
    @Subscribe
    public void handleEzDoChangedEvent(EzDoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEzDo(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleEzDoDirectoryChangedEvent(EzDoDirectoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Directory changed, saving to new directory at: "
                + event.getPath()));
        String oldPath = config.getEzDoFilePath();
        try {
            moveEzDo(oldPath, event.getPath());
            config.setEzDoFilePath(event.getPath());
            setEzDoFilePath(event.getPath());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ioe) {
            config.setEzDoFilePath(oldPath);
            raise (new DataSavingExceptionEvent(ioe));
        }
    }

    @Override
    public void moveEzDo(String oldPath, String newPath) throws IOException {
        ezDoStorage.moveEzDo(oldPath, newPath);
    }

}
