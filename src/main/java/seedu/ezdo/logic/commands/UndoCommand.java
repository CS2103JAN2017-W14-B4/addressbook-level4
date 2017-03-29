package seedu.ezdo.logic.commands;

import java.util.EmptyStackException;

import seedu.ezdo.logic.commands.exceptions.CommandException;
//@@author A0139248X
/*
 * Undo the last undoable command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String SHORT_COMMAND_WORD = "u";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous undoable command.";
    public static final String MESSAGE_SUCCESS = "Previous undoable command has been undone!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no previous undoable command!";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }
    }

}
