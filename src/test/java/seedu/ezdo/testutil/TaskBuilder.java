package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new StartDate(startDate));
        return this;
    }

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.task.setPhone(new Priority(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Email(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
