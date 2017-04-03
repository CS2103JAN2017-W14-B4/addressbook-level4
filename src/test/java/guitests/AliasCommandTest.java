//@@author A0138907W
package guitests;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ezdo.logic.commands.AliasCommand.MESSAGE_ALIAS_ALREADY_IN_USE;
import static seedu.ezdo.logic.commands.AliasCommand.MESSAGE_COMMAND_DOES_NOT_EXIST;
import static seedu.ezdo.logic.commands.AliasCommand.MESSAGE_RESET_SUCCESS;

import org.junit.Test;

import seedu.ezdo.logic.commands.AliasCommand;

public class AliasCommandTest extends EzDoGuiTest {

    @Test
    public void alias_invalidCommand_fail() {
        //invalid command
        commandBox.runCommand("alias");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));

        //mapping to an alias used by another command
        commandBox.runCommand("alias add k");
        assertResultMessage(MESSAGE_ALIAS_ALREADY_IN_USE);

        //mapping a non-existent command
        commandBox.runCommand("alias zxc zzz");
        assertResultMessage(MESSAGE_COMMAND_DOES_NOT_EXIST);
    }

    @Test
    public void alias_success() {
        //correctly aliasing a command
        commandBox.runCommand("alias clear kaboom");
        commandBox.runCommand("kaboom");
        assertListSize(0);

        //correctly aliasing a short command
        commandBox.runCommand("alias add aaa");
        commandBox.runCommand("aaa sample task s/today d/tomorrow p/1");
        assertListSize(1);
    }

    @Test
    public void alias_reset() {
        // correctly aliasing commands
        commandBox.runCommand("alias add aaa");
        commandBox.runCommand("alias edit zzz");

        // clearing all aliases
        commandBox.runCommand("alias reset");
        assertResultMessage(MESSAGE_RESET_SUCCESS);

        // old aliases do not work anymore
        commandBox.runCommand("aaa");
        assertResultMessage(MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("zzz");
        assertResultMessage(MESSAGE_UNKNOWN_COMMAND);
    }

}
