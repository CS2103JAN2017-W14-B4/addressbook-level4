package seedu.ezdo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.model.ReadOnlyEzDo;
//@@author A0139248X
/**
 * A class to access EzDo data stored as an xml file on the hard disk.
 */
public class XmlEzDoStorage implements EzDoStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEzDoStorage.class);

    private String filePath;

    public XmlEzDoStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getEzDoFilePath() {
        return filePath;
    }

    @Override
    public void setEzDoFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException {
        return readEzDo(filePath);
    }

    /**
     * Similar to {@link #readEzDo()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyEzDo> readEzDo(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File ezDoFile = new File(filePath);

        if (!ezDoFile.exists()) {
            logger.info("EzDo file "  + ezDoFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEzDo ezDoOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(ezDoOptional);
    }

    @Override
    public void saveEzDo(ReadOnlyEzDo ezDo) throws IOException {
        saveEzDo(ezDo, filePath);
    }

    /**
     * Similar to {@link #saveEzDo(ReadOnlyEzDo)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveEzDo(ReadOnlyEzDo ezDo, String filePath) throws IOException {
        assert ezDo != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEzDo(ezDo));
    }

    @Override
    public void moveEzDo(String oldPath, String newPath) throws IOException {
        assert oldPath != null;
        assert newPath != null;
        try {
            Files.move(Paths.get(oldPath), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            logger.info("I/O Exception when moving ezDo.xml to new directory.");
            throw new IOException("Error moving file to new directory.");
        }
    }
}
