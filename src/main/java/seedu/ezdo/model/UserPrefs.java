package seedu.ezdo.model;

import java.util.Objects;

import seedu.ezdo.commons.core.GuiSettings;
import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;
import seedu.ezdo.logic.CommandAliases;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public SortCriteria sortCriteria;
    public Boolean isSortedAscending;
    public CommandAliases commandAliases;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
        this.sortCriteria = UniqueTaskList.SortCriteria.NAME;
        this.isSortedAscending = true;
        this.commandAliases = new CommandAliases();
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
  //@@author A0139248X
    public void updateLastUsedSortCriteria(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public SortCriteria getSortCriteria() {
        return sortCriteria;
    }
  //@@author
    public void addCommandAlias(String command, String alias) throws AliasAlreadyInUseException,
        CommandDoesNotExistException {
        commandAliases.addAlias(command, alias);
    }

    public CommandAliases getCommandAliases() {
        return commandAliases;
    }

    public void updateLastUsedIsSortedAscending(Boolean isSortedAscending) {
        this.isSortedAscending = isSortedAscending;
    }

    public boolean getIsSortedAscending() {
        return isSortedAscending;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        return guiSettings.toString();
    }

}
