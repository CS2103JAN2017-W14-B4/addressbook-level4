package seedu.ezdo.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.XmlUtil;
//@@author A0139248X
/**
 * Stores ezDo data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given ezDo data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableEzDo ezDo)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, ezDo);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns ezDo in the file or an empty ezDo
     */
    public static XmlSerializableEzDo loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableEzDo.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
