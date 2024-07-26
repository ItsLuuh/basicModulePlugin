package net.luuh.test.utils;

import net.luuh.test.Helper;
import net.luuh.test.Main;
import org.bukkit.entity.Player;

public class LPUtils {

    private static final Helper helper = Main.getPlugin().getHelper();

    public static String getPlayerPrimaryGroup(Player player) {
        net.luckperms.api.model.user.User luckUser = helper.getLuckPerms().getUserManager().getUser(player.getName());
        if(luckUser == null) return "default";
        return luckUser.getPrimaryGroup();
    }

}
