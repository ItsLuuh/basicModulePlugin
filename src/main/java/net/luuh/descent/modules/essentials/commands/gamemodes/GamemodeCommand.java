package net.luuh.descent.modules.essentials.commands.gamemodes;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.CommandSuccessException;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.constants.Permission;
import net.luuh.descent.modules.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GamemodeCommand extends ModuleCommand<Essentials> {

    public GamemodeCommand(Helper helper, Essentials module) {
        super(helper, module, "gamemode", "gm");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        Player target;
        GameMode gamemode = player.getGameMode();

        if(args.length == 1){

            gamemode = switch (args[0].toLowerCase()) {
                case "creative", "c" -> GameMode.CREATIVE;
                case "survival", "s" -> GameMode.SURVIVAL;
                case "adventure", "a" -> GameMode.ADVENTURE;
                case "spectator", "sp" -> GameMode.SPECTATOR;
                default -> gamemode;
            };
            GamemodeCommand.changePlayerGamemode(player, helper, gamemode);

        } else {

            if (!Permission.GAMEMODE_OTHERS.has(player)) {
                player.sendMessage(helper.getRMUtils().readTranslation(player, "case-no-perms"));
                throw new CommandSuccessException(false);
            }
            if (Bukkit.getPlayer(args[1]) != null) {
                target = Bukkit.getPlayer(args[1]);
                changeTargetGamemode(player, target, helper, gamemode);
            } else {
                player.sendMessage(helper.getRMUtils().readTranslation("case-invalid-args"));
                throw new CommandSuccessException(false);
            }
        }
    }

    public static void changePlayerGamemode(Player player, Helper helper, GameMode gameMode) throws CommandSuccessException {
        String perm = "GAMEMODE_" + gameMode.toString().toUpperCase();
        if(!Permission.valueOf(perm).has(player)) {
            player.sendMessage(helper.getRMUtils().readTranslation("case-no-perms"));
            throw new CommandSuccessException(false);
        }
        player.setGameMode(gameMode);
        player.sendMessage(helper.getRMUtils().readTranslation(player,"gamemode"));
        throw new CommandSuccessException(true);
    }

    public static void changeTargetGamemode(Player player, Player target, Helper helper, GameMode gameMode) throws CommandSuccessException {
        String perm = "GAMEMODE_" + gameMode.name().toUpperCase();
        if(!Permission.valueOf(perm).has(player) && !Permission.GAMEMODE_OTHERS.has(player)) {
            player.sendMessage(helper.getRMUtils().readTranslation("case-no-perms"));
            throw new CommandSuccessException(false);
        }
        target.setGameMode(gameMode);

        Map<String, String> replace = new HashMap<>();
        replace.put("%a1", target.getName());
        replace.put("%a2", gameMode.name());

        String pmessage = helper.getRMUtils().replaceArgs(helper.getRMUtils().readTranslationString("gamemode-others"), replace);
        player.sendMessage(pmessage);
        target.sendMessage(helper.getRMUtils().readTranslation("gamemode"));
        throw new CommandSuccessException(true);
    }
}
