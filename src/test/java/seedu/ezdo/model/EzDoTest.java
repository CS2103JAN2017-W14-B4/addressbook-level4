package seedu.ezdo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.testutil.TypicalTestTasks;

public class EzDoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EzDo ezDo = new EzDo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ezDo.getTaskList());
        assertEquals(Collections.emptyList(), ezDo.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        ezDo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEzDo_replacesData() {
        EzDo newData = new TypicalTestTasks().getTypicalEzDo();
        ezDo.resetData(newData);
        assertEquals(newData, ezDo);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        EzDoStub newData = new EzDoStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        List<ReadOnlyTask> newTasks = typicalEzDo.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalEzDo.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        EzDoStub newData = new EzDoStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }
  //@@author A0139248X
    @Test
    public void hashCode_works() {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        EzDo typicalEzDo2 = new TypicalTestTasks().getTypicalEzDo();
        assertEquals(typicalEzDo.hashCode(), typicalEzDo2.hashCode());
    }
  //@@author
    /**
     * A stub ReadOnlyEzDo whose tasks and tags lists can violate interface constraints.
     */
    private static class EzDoStub implements ReadOnlyEzDo {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        EzDoStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
