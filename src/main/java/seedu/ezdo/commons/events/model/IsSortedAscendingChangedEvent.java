//@@author A0138907W
package seedu.ezdo.commons.events.model;

import seedu.ezdo.commons.events.BaseEvent;

/** Indicates that the IsSortedAscending variable in the model has changed */
public class IsSortedAscendingChangedEvent extends BaseEvent {

    private final Boolean isSortedAscending;

    public IsSortedAscendingChangedEvent(Boolean isSortedAscending) {
        this.isSortedAscending = isSortedAscending;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Boolean getNewIsSortedAscending() {
        return isSortedAscending;
    }
}
