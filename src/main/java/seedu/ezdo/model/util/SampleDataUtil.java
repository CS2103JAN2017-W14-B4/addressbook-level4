package seedu.ezdo.model.util;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy one cherry"), new Priority("1"), new Email("1alexyeoh@gmail.com"),
                    new StartDate("11/01/2017"),
                    new UniqueTagList("groceries")),
                new Task(new Name("Study for two midterms"), new Priority("2"), new Email("2berniceyu@gmail.com"),
                    new StartDate("12/02/2017"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Buy ps three"), new Priority("3"), new Email("3charlotte@yahoo.com"),
                    new StartDate("13/03/2017"),
                    new UniqueTagList("personal")),
                new Task(new Name("Visit four neighbours"), new Priority("2"), new Email("4lidavid@google.com"),
                    new StartDate("14/04/2017"),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for five finals"), new Priority("3"), new Email("5irfan@outlook.com"),
                    new StartDate("15/05/2017"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Prepare six presentations"), new Priority("3"), new Email("6royb@gmail.com"),
                    new StartDate("16/06/2017"),
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
