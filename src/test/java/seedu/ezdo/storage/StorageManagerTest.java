package seedu.ezdo.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.ezdo.commons.core.Config;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
import seedu.ezdo.testutil.EventsCollector;
import seedu.ezdo.testutil.TypicalTestTasks;

public class StorageManagerTest {

    private StorageManager storageManager;
    private Config config;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        config = new Config();
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"), config);
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void ezDoReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlEzDoStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlEzDoStorageTest} class.
         */
        EzDo original = new TypicalTestTasks().getTypicalEzDo();
        storageManager.saveEzDo(original);
        ReadOnlyEzDo retrieved = storageManager.readEzDo().get();
        assertEquals(original, new EzDo(retrieved));
    }

    @Test
    public void getEzDoFilePath() {
        assertNotNull(storageManager.getEzDoFilePath());
    }

    @Test
    public void handleEzDoChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlEzDoStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"), config);
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEzDoChangedEvent(new EzDoChangedEvent(new EzDo()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
  //@@author A0139248X
    @Test
    public void handleEzDoDirectoryChangedEvent_exceptionThrown_eventRaised() throws IOException {
        Storage storage = new StorageManager(new XmlEzDoStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), config);
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEzDoDirectoryChangedEvent(new EzDoDirectoryChangedEvent("dummy path"));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save or move method is called
     */
    class XmlEzDoStorageExceptionThrowingStub extends XmlEzDoStorage {

        public XmlEzDoStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveEzDo(ReadOnlyEzDo ezDo, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void moveEzDo(String oldPath, String newPath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
