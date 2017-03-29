package seedu.ezdo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.CommandResult;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.logic.parser.Parser;
import seedu.ezdo.model.Model;
import seedu.ezdo.model.UserPrefs;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage, UserPrefs prefs) {
        this.model = model;
        this.parser = new Parser(prefs.getCommandAliases());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
