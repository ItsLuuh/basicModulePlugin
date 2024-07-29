package net.luuh.test.modules.essentials.commands;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.CommandSuccessException;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.constants.Permission;
import net.luuh.test.modules.essentials.Essentials;
import org.bukkit.entity.Player;

public class CoreCommand extends ModuleCommand<Essentials> {
    public CoreCommand(Helper helper, Essentials module) {
        super(helper, module, "core");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {

        if(args.length == 0) {
            player.sendMessage(
                    helper.getRMUtils().readTranslation(player, "core-message")
            );
            throw new CommandSuccessException(false);
        }

        switch (args[0]) {
            case "reload":

                // RELOAD CASE
                if (!(Permission.RELOAD.has(player) || Permission.RELOAD_CONFIG.has(player) || Permission.RELOAD_MEX.has(player))) {
                    player.sendMessage(helper.getRMUtils().readTranslation(player, "case-no-perms"));
                    throw new CommandSuccessException(false);
                }

                if(args.length == 1) {
                    player.sendMessage(helper.getRMUtils().readTranslation(player,"core-reload-all"));
                    helper.reloadConfig(0);
                    throw new CommandSuccessException(true);
                }

                switch (args[1]) {
                    case "all":
                    default:
                        helper.reloadConfig(0);
                        player.sendMessage(helper.getRMUtils().readTranslation(player,"core-reload-all"));
                        throw new CommandSuccessException(true);
                    case "config":
                        helper.reloadConfig(1);
                        player.sendMessage(helper.getRMUtils().readTranslation(player,"core-reload-config"));
                        throw new CommandSuccessException(true);
                    case "mex":
                        helper.reloadConfig(2);
                        player.sendMessage(helper.getRMUtils().readTranslation(player,"core-reload-mex"));
                        throw new CommandSuccessException(true);
                }

            case "help":
                // HELP CASE
                player.sendMessage(helper.getRMUtils().readTranslation(player,"core-help"));
                throw new CommandSuccessException(true);
        }
    }
}
