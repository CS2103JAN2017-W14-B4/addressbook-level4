package seedu.ezdo.commons.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessagesTest {

    @Test
    public void correctMessages() {
        String message = Messages.MESSAGE_UNKNOWN_COMMAND;
        assertEquals(message, "Unknown command");
        message = Messages.MESSAGE_INVALID_COMMAND_FORMAT;
        assertEquals(message, "Invalid command format! \n%1$s");
        message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        assertEquals(message, "The task index provided is invalid");
        message = Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
        assertEquals(message, "%1$d tasks listed!");
    }
}
