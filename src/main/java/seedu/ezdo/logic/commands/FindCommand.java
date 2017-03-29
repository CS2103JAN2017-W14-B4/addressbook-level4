package seedu.ezdo.logic.commands;

import java.util.ArrayList;
//@@author A0141010L
/**
 * Finds and lists all tasks in ezDo whose name contains any of the argument
 * keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " buy milk clean p/3";

    private final ArrayList<Object> listToCompare;
    private final ArrayList<Boolean> searchIndicatorList;

    public FindCommand(ArrayList<Object> listToCompare, ArrayList<Boolean> searchIndicatorList) {
        this.listToCompare = listToCompare;
        this.searchIndicatorList = searchIndicatorList;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(listToCompare, searchIndicatorList);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
