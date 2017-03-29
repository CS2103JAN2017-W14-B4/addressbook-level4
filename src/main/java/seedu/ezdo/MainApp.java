package seedu.ezdo;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.ezdo.commons.core.Config;
import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.core.Version;
import seedu.ezdo.commons.events.model.IsSortedAscendingChangedEvent;
import seedu.ezdo.commons.events.model.SortCriteriaChangedEvent;
import seedu.ezdo.commons.events.ui.ExitAppRequestEvent;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.ConfigUtil;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.logic.Logic;
import seedu.ezdo.logic.LogicManager;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.Model;
import seedu.ezdo.model.ModelManager;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
import seedu.ezdo.model.util.SampleDataUtil;
import seedu.ezdo.storage.Storage;
import seedu.ezdo.storage.StorageManager;
import seedu.ezdo.ui.Ui;
import seedu.ezdo.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing EzDo ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getEzDoFilePath(), config.getUserPrefsFilePath(), config);

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage, userPrefs);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyEzDo> ezDoOptional;
        ReadOnlyEzDo initialData;
        try {
            ezDoOptional = storage.readEzDo();
            if (!ezDoOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample EzDo");
            }
            initialData = ezDoOptional.orElseGet(SampleDataUtil::getSampleEzDo);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty EzDo");
            initialData = new EzDo();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty EzDo");
            initialData = new EzDo();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty EzDo");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting EzDo " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping EzDo ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }
  //@@author A0139248X
    @Subscribe
    public void handleSortCriteriaChangedEvent(SortCriteriaChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.userPrefs.updateLastUsedSortCriteria(event.getNewSortCriteria());
    }
  //@@author
    @Subscribe
    public void handleIsSortedAscendingChangedEvent(IsSortedAscendingChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.userPrefs.updateLastUsedIsSortedAscending(event.getNewIsSortedAscending());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
