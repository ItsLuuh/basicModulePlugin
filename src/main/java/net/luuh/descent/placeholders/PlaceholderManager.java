package net.luuh.descent.placeholders;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.luuh.descent.Helper;
import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
import net.luuh.descent.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PlaceholderManager {

    private final Helper helper;

    public PlaceholderManager(Helper helper) {
        this.helper = helper;
    }

    private String onIMGRequest(String params) {
        params = params.replaceAll("img", "");
        return new FontImageWrapper(params).getString();

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

    private String onRequest(Player player, String params) throws ExecutionException, InterruptedException {
        Map<Class<? extends Attribute>, Attribute> attributes = AttributeManager.getAttributes();
        UserStats userStats = helper.getPlayerManager().getUser(player).getUserStats();
        return switch (params.toLowerCase()) {
            case "verion_uuid" -> player.getUniqueId().toString();
            case "verion_upt" -> helper.getPlayerManager().getUser(player).getUpt().toString();
            case "verion_playername", "verion_displayname" -> player.getName();
            case "verion_ping" -> String.valueOf(player.getPing());
            case "verion_health" -> String.valueOf(player.getHealth());
            case "verion_max_health" -> String.valueOf(player.getMaxHealth());
            case "verion_food_level" -> String.valueOf(player.getFoodLevel());
            case "verion_exp_level" -> String.valueOf(player.getLevel());
            case "verion_exp" -> String.valueOf(player.getExp());
            case "verion_world" -> player.getWorld().getName();
            case "verion_x" -> String.valueOf(player.getLocation().getX());
            case "verion_y" -> String.valueOf(player.getLocation().getY());
            case "verion_z" -> String.valueOf(player.getLocation().getZ());
            case "verion_yaw" -> String.valueOf(player.getLocation().getYaw());
            case "verion_pitch" -> String.valueOf(player.getLocation().getPitch());
            case "verion_ip" -> player.getAddress().getAddress().getHostAddress();
            case "verion_balance" ->
                    String.valueOf(helper.getPlayerManager().getUser(player).getUserEconomy().getVisual(EconomyType.BALANCE));
            case "verion_balance_formatted" ->
                    Util.formatNumberWithDot(helper.getPlayerManager().getUser(player).getUserEconomy().getVisual(EconomyType.BALANCE));
            case "verion_credits" ->
                    String.valueOf(helper.getPlayerManager().getUser(player).getUserEconomy().getVisual(EconomyType.CREDITS));
            case "verion_credits_formatted" ->
                    Util.formatNumberWithDot(helper.getPlayerManager().getUser(player).getUserEconomy().getVisual(EconomyType.CREDITS));
            case "verion_deaths" -> String.valueOf(player.getStatistic(Statistic.DEATHS));
            case "verion_mobkills" -> String.valueOf(player.getStatistic(Statistic.MOB_KILLS));
            case "verion_gamemode" -> player.getGameMode().name();
            case "verion_stats_strength" ->
                    String.valueOf(userStats.get(attributes.get(Strength.class), StatType.STRENGTH));
            case "verion_stats_melee_damage" ->
                    String.valueOf(userStats.get(attributes.get(MeleeDamage.class)));
            case "verion_stats_range" ->
                    String.valueOf(userStats.get(attributes.get(Range.class)));
            case "verion_stats_ability_damage" ->
                    String.valueOf(userStats.get(attributes.get(AbilityDamage.class)));
            case "verion_stats_defense" ->
                    String.valueOf(userStats.get(attributes.get(Defense.class), StatType.DEFENSE));
            case "verion_stats_health_regen" ->
                    String.valueOf(userStats.get(attributes.get(HealthRegen.class), StatType.HEALTH_REGEN));
            case "verion_stats_crit_chance" ->
                    String.valueOf(userStats.get(attributes.get(CritChance.class), StatType.CRIT_CHANCE));
            case "verion_stats_crit_damage" ->
                    String.valueOf(userStats.get(attributes.get(CritDamage.class), StatType.CRIT_DAMAGE));
            case "verion_stats_intelligence" ->
                    String.valueOf(userStats.getManaBar().getIntelligence());
            case "verion_stats_max_mana" ->
                    String.valueOf(userStats.getManaBar().getMaxMana());
            case "verion_stats_mana_damage" ->
                    String.valueOf(userStats.get(attributes.get(ManaDamage.class)));
            case "verion_stats_mana_regen" ->
                    String.valueOf(userStats.get(attributes.get(ManaRegen.class), StatType.MANA_REGEN));
            case "verion_stats_agility" ->
                    String.valueOf(userStats.get(attributes.get(Agility.class), StatType.AGILITY));
            case "verion_stats_player_speed" ->
                    String.valueOf(userStats.get(attributes.get(PlayerSpeed.class), StatType.PLAYER_SPEED));
            case "verion_stats_attack_speed" ->
                    String.valueOf(userStats.get(attributes.get(AttackSpeed.class)));
            default -> null;
        };
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
                    try {
                        value = onRequest(player, placeholder);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(placeholder.startsWith("img")) {
                        value = onIMGRequest(placeholder);
                    } else {
                        value = onRequest(placeholder);
                    }
                }

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
