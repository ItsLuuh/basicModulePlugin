package net.luuh.test.modules.essentials.commands.gamemodes;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.CommandSuccessException;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.modules.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpectatorCommand extends ModuleCommand<Essentials> {

    public SpectatorCommand(Helper helper, Essentials module) {
        super(helper, module, "gmsp", "spectator");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        Player target;
        GameMode gameMode = GameMode.SPECTATOR;
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
