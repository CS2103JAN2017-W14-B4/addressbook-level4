package seedu.ezdo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private String recur;
    @XmlElement(required = true)
    private boolean done;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        priority = source.getPriority().value;
        startDate = source.getStartDate().value;
        dueDate = source.getDueDate().value;
        recur = source.getRecur().value;
        done = source.getDone();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final TaskDate startDate = new StartDate(this.startDate);
        final TaskDate dueDate = new DueDate(this.dueDate);
        final Recur recur = new Recur(this.recur);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        Task toAdd = new Task(name, priority, startDate, dueDate, recur, tags);
        toAdd.setDone(this.done);
        return toAdd;
    }
}
