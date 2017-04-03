package seedu.ezdo.commons.events.storage;

import seedu.ezdo.commons.events.BaseEvent;

//@@author A0139248X
/**
 * Indicates a request to change the directory of the saved ezDo.xml
 */
public class EzDoDirectoryChangedEvent extends BaseEvent {

    private String path;

    public EzDoDirectoryChangedEvent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "New save directory: " + path;
    }
}
