package seedu.ezdo.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.ezdo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
//@@author A0139248X
public class EventsTest {

    @Test
    public void TaskPanelSelectionChangedEvent_getData() throws Exception {
        ReadOnlyTask task = new Task(new Name("hello"), new Priority(""),
                new StartDate(""), new DueDate(""), new UniqueTagList("what"));
        TaskPanelSelectionChangedEvent tpsce = new TaskPanelSelectionChangedEvent(task);
        assertEquals(tpsce.getNewSelection(), task);
    }
}
