package seedu.ezdo.model.task;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javafx.collections.ObservableList;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UniqueTaskListTest {

    UniqueTaskList utl = new UniqueTaskList();

    @Mock
    public ObservableList<Task> mock;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void remove_notFoundAndKilled() throws Exception {
        thrown.expect(TaskNotFoundException.class);
        Task task = new Task(new Name("lol"), new Priority("1"), new StartDate("today"), new DueDate("tomorrow"),
                new UniqueTagList("jesus"));
        when(mock.remove(task)).thenReturn(false);
        utl.remove(task);
    }

    @Test
    public void hashCode_equals() {
        assertTrue(utl.hashCode() == utl.hashCode());
    }

}
