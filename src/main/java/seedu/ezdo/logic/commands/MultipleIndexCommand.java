package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.model.todo.ReadOnlyTask;
//@@author A0139248X
/**
 * Interface for commands that can have multiple indexes (kill, done)
 *
 */
public interface MultipleIndexCommand {
    /** checks if the indexes specified are all smaller than the size of the list and not 0 i.e. valid */
    boolean isIndexValid(UnmodifiableObservableList<ReadOnlyTask> lastShownList);
}
