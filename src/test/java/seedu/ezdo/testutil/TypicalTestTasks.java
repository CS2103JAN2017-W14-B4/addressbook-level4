package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withStartDate("1/6/2017").withEmail("alice@gmail.com")
                    .withPriority("1")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withStartDate("12/12/2017")
                    .withEmail("johnd@gmail.com").withPriority("1")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("2")
                    .withEmail("heinz@yahoo.com").withStartDate("02/12/2017").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("2")
                    .withEmail("cornelia@google.com").withStartDate("2/12/2017").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("3")
                    .withEmail("werner@gmail.com").withStartDate("1/12/2017").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("2")
                    .withEmail("lydia@gmail.com").withStartDate("13/12/2017").build();
            george = new TaskBuilder().withName("George Best").withPriority("3")
                    .withEmail("anna@google.com").withStartDate("21/12/2017").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("1")
                    .withEmail("stefan@mail.com").withStartDate("1/1/2020").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("2")
                    .withEmail("hans@google.com").withStartDate("1/1/2020").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEzDoWithSampleData(EzDo ez) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ez.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public EzDo getTypicalEzDo() {
        EzDo ez = new EzDo();
        loadEzDoWithSampleData(ez);
        return ez;
    }
}
