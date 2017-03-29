package seedu.ezdo.storage;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.util.XmlUtil;
//@@author A0139248X
@RunWith(PowerMockRunner.class)
@PrepareForTest({XmlFileStorage.class, XmlUtil.class})
public class XmlFileStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() throws Exception {
        thrown.expect(AssertionError.class);
        PowerMockito.mockStatic(XmlUtil.class);
        File file = new File("omg");
        XmlSerializableEzDo ezDo = new XmlSerializableEzDo();
        PowerMockito.doThrow(new JAXBException("exception")).when(XmlUtil.class, "saveDataToFile", file, ezDo);
        XmlFileStorage.saveDataToFile(file, ezDo);
    }

}
