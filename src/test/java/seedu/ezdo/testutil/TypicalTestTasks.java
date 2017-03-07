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
                    .withStartDate("1/6/2017").withPriority("1")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withStartDate("12/12/2017")
                    .withDueDate("12/02/2015").withPriority("1")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("2").withDueDate("13/03/2015")
                    .withStartDate("12/12/2017").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("2").withDueDate("14/04/2015")
                    .withStartDate("12/12/2017").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("3").withDueDate("15/05/2015")
                    .withStartDate("12/12/2017").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("2").withDueDate("16/06/2015")
                    .withStartDate("12/12/2017").build();
            george = new TaskBuilder().withName("George Best").withPriority("3").withDueDate("17/07/2015")
                    .withStartDate("12/12/2017").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("1").withDueDate("18/08/2015")
                    .withStartDate("12/12/2017").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("2").withDueDate("19/09/2015")
                    .withStartDate("12/12/2017").build();
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
