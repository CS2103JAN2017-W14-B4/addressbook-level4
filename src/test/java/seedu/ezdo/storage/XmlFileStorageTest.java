package seedu.ezdo.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.commons.util.XmlUtil;

//@@author A0139248X
@RunWith(JMockit.class)
public class XmlFileStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void saveDataToFile_JAXBException_throwAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        File file = new File("omg");
        XmlSerializableEzDo ezDo = new XmlSerializableEzDo();
        new MockUp<XmlUtil>()
        {
            @Mock
            <T> void saveDataToFile(File file, T data) throws FileNotFoundException, JAXBException
            {
                throw new JAXBException("exception");
            }
        };
        XmlFileStorage.saveDataToFile(file, ezDo);
    }
}
