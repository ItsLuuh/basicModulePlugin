package net.luuh.descent.modules.essentials.commands.gamemodes;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.CommandSuccessException;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.modules.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CreativeCommand extends ModuleCommand<Essentials> {

    public CreativeCommand(Helper helper, Essentials module) {
        super(helper, module, "gmc", "creative");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        Player target;
        GameMode gameMode = GameMode.CREATIVE;
        if (args.length > 0) {
            if(Bukkit.getPlayer(args[0]) == null && Bukkit.getPlayer(args[0]).isOnline()) {
                player.sendMessage(helper.getRMUtils().readTranslation("case-invalid-args"));
                return;
            }
            target = Bukkit.getPlayer(args[0]);
            GamemodeCommand.changeTargetGamemode(player, target, helper, gameMode);
        }
        else GamemodeCommand.changePlayerGamemode(player, helper, gameMode);
    }
}
