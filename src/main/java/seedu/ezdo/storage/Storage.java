package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
//@@author A0139248X
/**
 * API of the Storage component
 */
public interface Storage extends EzDoStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getEzDoFilePath();

    @Override
    void setEzDoFilePath(String path);

    @Override
    Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException;

    @Override
    void saveEzDo(ReadOnlyEzDo ezDo) throws IOException;

    /**
     * Saves the current version of the EzDo to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEzDoChangedEvent(EzDoChangedEvent ezce);

    /**
     * Changes the current directory of the ezDo
     */
    void handleEzDoDirectoryChangedEvent(EzDoDirectoryChangedEvent ezdce);
}
