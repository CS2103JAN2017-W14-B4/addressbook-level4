package seedu.ezdo.commons.events.model;

import seedu.ezdo.commons.events.BaseEvent;
import seedu.ezdo.model.ReadOnlyEzDo;

/**
 * Indicates the EzDo in the model has changed.
 */
public class EzDoChangedEvent extends BaseEvent {

    public final ReadOnlyEzDo data;

    public EzDoChangedEvent(ReadOnlyEzDo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
