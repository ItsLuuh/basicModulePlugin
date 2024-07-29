package net.luuh.test.modules.essentials.commands;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.CommandSuccessException;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.constants.Permission;
import net.luuh.test.modules.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class FlyCommand extends ModuleCommand<Essentials> {
    public FlyCommand(Helper helper, Essentials module) {
        super(helper, module, "fly", "flight");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        if(!Permission.FLY.has(player)) return;
        if(args.length > 0) {
            Player target;
            if(args[1] != null && Permission.FLY_OTHERS.has(player) && Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) target = Bukkit.getPlayer(args[1]);
            else {
                target = player;
            }
            if(args[0].equalsIgnoreCase("on")) {

                // ON

                target.setAllowFlight(true);

                // If the player is not the target, send a message to the player
                if(player != target)
                    player.sendMessage(
                            helper.getRMUtils().replaceArgs(
                                    helper.getRMUtils().readTranslationString(player, "fly-other-on"),
                                    Map.of("%a1", target.getName()))
                    );

                // Send a message to the target
                target.sendMessage(helper.getRMUtils().readTranslation("fly-other-on"));
                throw new CommandSuccessException(true);
            } else if(args[0].equalsIgnoreCase("off")) {

                // OFF

                target.setAllowFlight(false);

                // If the player is not the target, send a message to the player
                if(player != target)
                    player.sendMessage(
                            helper.getRMUtils().replaceArgs(
                                    helper.getRMUtils().readTranslationString(player, "fly-other-off"),
                                    Map.of("%a1", target.getName()))
                    );

                // Send a message to the target
                target.sendMessage(helper.getRMUtils().readTranslation(player,"fly-off"));
                throw new CommandSuccessException(false);
            }
        } else {
            player.setAllowFlight(!player.getAllowFlight());
            player.sendMessage(player.getAllowFlight() ?
                    helper.getRMUtils().readTranslation(player,"fly-on") :
                    helper.getRMUtils().readTranslation(player,"fly-off"));
            throw new CommandSuccessException(player.getAllowFlight());
        }
    }
}
