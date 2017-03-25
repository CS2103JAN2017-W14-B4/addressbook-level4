package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.model.todo.ReadOnlyTask;

/**
 * Interface for commands that can have multiple indexes (kill, done)
 *
 */
public interface MultipleIndexCommand {
    boolean isIndexValid(UnmodifiableObservableList<ReadOnlyTask> lastShownList);
}
