package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, jack;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withStartDate("12/12/2016 11:22")
                    .withPriority("1").withDueDate("14/03/2017 22:33")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withStartDate("11/11/2015 14:33")
                    .withDueDate("12/02/2017 23:22").withPriority("1")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz")
                    .withPriority("2").withDueDate("13/03/2014 00:34")
                    .withStartDate("12/12/2015 05:32").build();
            daniel = new TaskBuilder().withName("Daniel Meier")
                    .withPriority("2").withDueDate("14/04/2016 15:17")
                    .withStartDate("12/12/2017 18:22").build();
            elle = new TaskBuilder().withName("Elle Meyer")
                    .withPriority("3").withDueDate("15/05/2015 20:20")
                    .withStartDate("01/03/2017 00:33").build();
            fiona = new TaskBuilder().withName("Fiona Kunz")
                    .withPriority("2").withDueDate("16/06/2016 17:17")
                    .withStartDate("12/12/2017 14:15").build();
            george = new TaskBuilder().withName("George Best")
                    .withPriority("3").withDueDate("17/07/2015 22:22")
                    .withStartDate("02/07/2017 04:55").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier")
                    .withPriority("1").withDueDate("18/08/2017 17:44")
                    .withStartDate("12/01/2017 11:12").build();
            ida = new TaskBuilder().withName("Ida Mueller")
                    .withPriority("2").withDueDate("19/09/2017 02:00")
                    .withStartDate("12/12/2017 13:31").build();
            jack = new TaskBuilder().withName("Jack Bauer")
                    .withPriority("2").withDueDate("12/09/2015 15:15")
                    .withStartDate("12/1/2013 00:00").build();
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

    public TestTask[] getTypicalDoneTasks() {
        return new TestTask[]{};
    }

    public EzDo getTypicalEzDo() {
        EzDo ez = new EzDo();
        loadEzDoWithSampleData(ez);
        return ez;
    }

}
