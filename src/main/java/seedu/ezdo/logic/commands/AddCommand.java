package seedu.ezdo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 * Adds a task to ezDo.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String SHORT_COMMAND_WORD = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to ezDo. "
            + "Parameters: NAME [p/PRIORITY] [s/START_DATE] [d/DUE_DATE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/1 s/12/12/2017 d/01/01/2018 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in ezDo";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String startDate, String dueDate, String recur, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Priority(priority),
                new StartDate(startDate),
                new DueDate(dueDate),
                new Recur(recur),
                new UniqueTagList(tagSet)
        );
    }
  //@@author A0139248X
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            int index = lastShownList.lastIndexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (DateException de) {
            throw new CommandException(Messages.MESSAGE_TASK_DATES_INVALID);
        }

    }

}
