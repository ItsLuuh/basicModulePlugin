package net.luuh.descent.abstraction.modules;

import net.luuh.descent.constants.CommandsCaseExceptions;
import net.luuh.descent.utils.RMUtils;
import org.bukkit.entity.Player;

public class CommandSuccessException extends Exception {
    private final boolean success;

    // Constructor that accepts a boolean and a message
    public CommandSuccessException(boolean success, String message) {
        super(message);
        this.success = success;
    }

    // Constructor that accepts a boolean, a message and a player
    public CommandSuccessException(boolean success, RMUtils rmUtils, CommandsCaseExceptions caseEx, Player player) {
        this.success = success;
        player.sendMessage(rmUtils.readTranslation(player, caseEx.getPermission()));
    }

    public CommandSuccessException(boolean success) {
        this.success = success;
    }

    // Constructor that accepts a boolean and a cause
    public boolean isSuccess() {
        return success;
    }
}
