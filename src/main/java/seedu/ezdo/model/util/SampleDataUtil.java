package seedu.ezdo.model.util;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy one cherry fruit"), 
                    new Priority("1"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/13/2018"),
                    new Recur("daily"),
                    new UniqueTagList("groceries")),
                new Task(new Name("Study for two midterms"), new Priority("2"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/09/2018"),
                    new Recur("weekly"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Buy ps three"), new Priority("3"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/10/2018"),
                    new Recur("monthly"),
                    new UniqueTagList("personal")),
                new Task(new Name("Visit four neighbours"), new Priority("2"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/11/2018"),
                    new Recur("yearly"),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for five finals"), new Priority("3"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/12/2018"),
                    new Recur("daily"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Prepare six presentations"), new Priority("3"),
                    new StartDate("12/12/2017"),
                    new DueDate("12/12/2018"),
                    new Recur("weekly"),
                    new UniqueTagList("school", "exams"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyEzDo getSampleEzDo() {
        try {
            EzDo sampleEzDo = new EzDo();
            for (Task sampleTask : getSampleTasks()) {
                sampleEzDo.addTask(sampleTask);
            }
            return sampleEzDo;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
