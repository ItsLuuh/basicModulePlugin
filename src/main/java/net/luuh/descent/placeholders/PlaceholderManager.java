package net.luuh.descent.placeholders;

import net.luuh.descent.Helper;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;

public class PlaceholderManager {

    private final Helper helper;

    public PlaceholderManager(Helper helper) {
        this.helper = helper;
    }

    private String onRequest(String params) {
        switch(params.toLowerCase()) {
            case "server_coreversion":
                helper.getPlugin().getPluginMeta().getVersion();
            case "server_servername":
                return Bukkit.getServer().getName();
            case "server_online":
                return String.valueOf(Bukkit.getOnlinePlayers().size());
            case "server_maxplayers":
                return String.valueOf(Bukkit.getMaxPlayers());
            case "server_version":
                return Bukkit.getVersion();
            case "server_tps":
                return String.valueOf(Bukkit.getTPS()[0]);
            case "server_tps1":
                return String.valueOf(Bukkit.getTPS()[1]);
            case "server_tps5":
                return String.valueOf(Bukkit.getTPS()[5]);
            case "server_tps15":
                return String.valueOf(Bukkit.getTPS()[15]);
            case "server_server_uptime":
                return String.valueOf(ManagementFactory.getRuntimeMXBean().getUptime());
            case "server_server_ram_used":
                return String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            case "server_server_ram_free":
                return String.valueOf(Runtime.getRuntime().freeMemory());
            case "server_server_ram_total":
                return String.valueOf(Runtime.getRuntime().totalMemory());
            case "server_server_ram_max":
                return String.valueOf(Runtime.getRuntime().maxMemory());
            default:
                return null;
        }
    }

    private String onRequest(Player player, String params) {
        switch(params.toLowerCase()) {
            case "verion_uuid":
                return player.getUniqueId().toString();
            case "verion_upt":
                return helper.getPlayerManager().getUser(player).getUpt().toString();
            case "verion_playername", "verion_displayname":
                return player.getName();
            case "verion_ping":
                return String.valueOf(player.getPing());
            case "verion_health":
                return String.valueOf(player.getHealth());
            case "verion_max_health":
                return String.valueOf(player.getMaxHealth());
            case "verion_food_level":
                return String.valueOf(player.getFoodLevel());
            case "verion_exp_level":
                return String.valueOf(player.getLevel());
            case "verion_exp":
                return String.valueOf(player.getExp());
            case "verion_world":
                return player.getWorld().getName();
            case "verion_x":
                return String.valueOf(player.getLocation().getX());
            case "verion_y":
                return String.valueOf(player.getLocation().getY());
            case "verion_z":
                return String.valueOf(player.getLocation().getZ());
            case "verion_yaw":
                return String.valueOf(player.getLocation().getYaw());
            case "verion_pitch":
                return String.valueOf(player.getLocation().getPitch());
            case "verion_ip":
                return player.getAddress().getAddress().getHostAddress();
            case "verion_balance":
                return String.valueOf(helper.getPlayerManager().getUser(player).getUserEconomy().get(EconomyType.BALANCE).getNow(0d));
            case "verion_balance_formatted":
                return Util.formatNumberWithDot(helper.getPlayerManager().getUser(player).getUserEconomy().get(EconomyType.BALANCE).getNow(0d));
            case "verion_credits":
                return String.valueOf(helper.getPlayerManager().getUser(player).getUserEconomy().get(EconomyType.CREDITS).getNow(0d));
            case "verion_credits_formatted":
                return Util.formatNumberWithDot(helper.getPlayerManager().getUser(player).getUserEconomy().get(EconomyType.CREDITS).getNow(0d));
            case "verion_deaths":
                return String.valueOf(player.getStatistic(Statistic.DEATHS));
            case "verion_mobkills":
                return String.valueOf(player.getStatistic(Statistic.MOB_KILLS));
            case "verion_gamemode":
                return player.getGameMode().name();

            default:
                return null;
        }
    }

    public String replace(Player player, String message) {
        String[] parts = message.split("%");
        StringBuilder replacedMessage = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (i % 2 == 0) {

                replacedMessage.append(parts[i]);
            } else {

                String placeholder = parts[i];
                String value = "";

                if (player != null) {
                    value = onRequest(player, placeholder);
                } else replacedMessage.append("%").append(placeholder).append("%");
                if (value != null) {
                    replacedMessage.append(value);
                } else {
                    replacedMessage.append("%").append(placeholder).append("%");
                }
            }
        }

        return replacedMessage.toString();
    }

    public String replace(String message) {
        String[] parts = message.split("%");
        StringBuilder replacedMessage = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (i % 2 == 0) {

                replacedMessage.append(parts[i]);
            } else {

                String placeholder = parts[i];
                String value;
                if (onRequest(placeholder) != null) {
                    value = onRequest(placeholder);
                    replacedMessage.append(value);
                } else {
                    replacedMessage.append("%").append(placeholder).append("%");
                }
            }
        }

        return replacedMessage.toString();
    }
}
