package seedu.ezdo.commons.events.model;

import seedu.ezdo.commons.events.BaseEvent;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

//@@author A0139248X
/**
 * Indicates that the SortCriteria in the model has changed.
 */
public class SortCriteriaChangedEvent extends BaseEvent {

    private final SortCriteria sortCriteria;

    public SortCriteriaChangedEvent(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SortCriteria getNewSortCriteria() {
        return sortCriteria;
    }
}
