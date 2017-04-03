package seedu.ezdo.model.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;
//@@author A0139248X
@RunWith(PowerMockRunner.class)
public class UniqueTaskListEnumTest {

    private UniqueTaskList utl = new UniqueTaskList();

    @Test(expected = AssertionError.class)
    @PrepareForTest(SortCriteria.class)
    public void unknownValueShouldThrowException() throws Exception {
        utl.add(new Task(new Name("lol"), new Priority("1"), new StartDate("today"), new DueDate("tomorrow"),
                new UniqueTagList("jesus")));
        utl.add(new Task(new Name("lasdfol"), new Priority("1"), new StartDate("today"), new DueDate("tomorrow"),
                new UniqueTagList("jesus")));

        SortCriteria C = PowerMockito.mock(SortCriteria.class);
        Whitebox.setInternalState(C, "name", "C");
        Whitebox.setInternalState(C, "ordinal", 4);

        PowerMockito.mockStatic(SortCriteria.class);
        PowerMockito.when(SortCriteria.values()).thenReturn(new SortCriteria[]
            {SortCriteria.NAME, SortCriteria.PRIORITY, SortCriteria.DUE_DATE, SortCriteria.START_DATE, C});

        utl.sortTasks(C, true);
    }
}
