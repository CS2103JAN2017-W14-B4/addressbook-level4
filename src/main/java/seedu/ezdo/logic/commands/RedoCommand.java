package seedu.ezdo.logic.commands;

import java.util.EmptyStackException;

import seedu.ezdo.logic.commands.exceptions.CommandException;
//@@author A0139248X
/*
 * Redo the last undone command
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String SHORT_COMMAND_WORD = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the last command you have undone.";
    public static final String MESSAGE_SUCCESS = "Last command undone has been redone!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no redoable command!";


    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }
    }

}
