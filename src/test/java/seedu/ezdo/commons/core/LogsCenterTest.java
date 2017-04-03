package seedu.ezdo.commons.core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

//@@author A0139248X
@RunWith(JMockit.class)
public class LogsCenterTest {

    @Test
    public void addFileHandler_IOException_logged() throws Exception {
        new MockUp<LogsCenter>()
        {
            @Mock
            FileHandler createFileHandler() throws IOException
            {
                throw new IOException();
            }
        };
        Logger logger = LogsCenter.getLogger("test logger");
        new Verifications() {
            {
                logger.warning("Error adding file handler for logger.");
            }
        };
    }
}
