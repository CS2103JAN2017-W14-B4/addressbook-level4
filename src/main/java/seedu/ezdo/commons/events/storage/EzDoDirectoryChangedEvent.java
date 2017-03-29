package seedu.ezdo.commons.events.storage;

import seedu.ezdo.commons.events.BaseEvent;
import seedu.ezdo.model.ReadOnlyEzDo;

//@@author A0139248X
/**
 * Indicates a request to change the directory of the saved ezDo.xml
 */
public class EzDoDirectoryChangedEvent extends BaseEvent {

    private ReadOnlyEzDo data;
    private String path;

    public EzDoDirectoryChangedEvent(ReadOnlyEzDo data, String path) {
        this.data = data;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ReadOnlyEzDo getData() {
        return data;
    }

    @Override
    public String toString() {
        return "New save directory: " + path;
    }
}
