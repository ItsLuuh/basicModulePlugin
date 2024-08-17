package net.luuh.descent.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.luuh.descent.Helper;
import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.utils.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PAPIHook extends PlaceholderExpansion {

    private final Helper helper;

    public PAPIHook(Helper helper) {
        this.helper = helper;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "verion";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Luuh";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        User user = helper.getUserManager().getUser(player.getName());
        if(params.contains("mini_")) {
            return Util.transformText(replaceCustomPlaceholders(player, extractContentAfterMini(params)));
        }
        return switch (params){
            case "stats_balance" -> user != null ? String.valueOf(user.getUserEconomy().getVisual(EconomyType.BALANCE)) : String.valueOf(0);
            case "stats_balance_formatted" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserEconomy().getVisual(EconomyType.BALANCE)) : String.valueOf(0);
            case "stats_balance_decimalformatted" -> user != null ? Util.formatNumberWithDot(user.getUserEconomy().getVisual(EconomyType.BALANCE)) : String.valueOf(0);

            case "stats_credits" -> user != null ? String.valueOf(user.getUserEconomy().getVisual(EconomyType.CREDITS)) : String.valueOf(0);
            case "stats_credits_formatted" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserEconomy().getVisual(EconomyType.CREDITS)) : String.valueOf(0);
            case "stats_credits_decimalformatted" -> user != null ? Util.formatNumberWithDot(user.getUserEconomy().getVisual(EconomyType.CREDITS)) : String.valueOf(0);

            case "stats_strength" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.STRENGTH, Strength.class)) : String.valueOf(0);
            case "stats_strength_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.STRENGTH, Strength.class)) : String.valueOf(0);

            case "stats_defense" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.DEFENSE, Defense.class)) : String.valueOf(0);
            case "stats_defense_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.DEFENSE, Defense.class)) : String.valueOf(0);

            case "stats_agility" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.AGILITY, Agility.class)) : String.valueOf(0);
            case "stats_agility_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.AGILITY, Agility.class)) : String.valueOf(0);

            case "stats_playerspeed" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.PLAYER_SPEED, PlayerSpeed.class)) : String.valueOf(0);
            case "stats_playerspeed_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.PLAYER_SPEED, PlayerSpeed.class)) : String.valueOf(0);

            case "stats_critchance" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.CRIT_CHANCE, CritChance.class)) : String.valueOf(0);
            case "stats_critchance_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.CRIT_CHANCE, CritChance.class)) : String.valueOf(0);

            case "stats_critdamage" -> user != null ? Util.formatNumberWithDot(user.getFinalStat(StatType.CRIT_DAMAGE, CritDamage.class)) : String.valueOf(0);
            case "stats_critdamage_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getFinalStat(StatType.CRIT_DAMAGE, CritDamage.class)) : String.valueOf(0);

            case "stats_meleedamage" -> user != null ? Util.formatNumberWithDot(user.getUserAttributes().get(MeleeDamage.class).get()) : String.valueOf(0);
            case "stats_meleedamage_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserAttributes().get(MeleeDamage.class).get()) : String.valueOf(0);

            case "stats_manadamage" -> user != null ? Util.formatNumberWithDot(user.getUserAttributes().get(ManaDamage.class).get()) : String.valueOf(0);
            case "stats_manadamage_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserAttributes().get(ManaDamage.class).get()) : String.valueOf(0);

            case "stats_abilitydamage" -> user != null ? Util.formatNumberWithDot(user.getUserAttributes().get(AbilityDamage.class).get()) : String.valueOf(0);
            case "stats_abilitydamage_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserAttributes().get(AbilityDamage.class).get()) : String.valueOf(0);

            case "stats_attackspeed" -> user != null ? Util.formatNumberWithDot(user.getUserAttributes().get(AttackSpeed.class).get()) : String.valueOf(0);
            case "stats_attackspeed_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserAttributes().get(AttackSpeed.class).get()) : String.valueOf(0);

            case "stats_range" -> user != null ? Util.formatNumberWithDot(user.getUserAttributes().get(Range.class).get()) : String.valueOf(0);
            case "stats_range_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getUserAttributes().get(Range.class).get()) : String.valueOf(0);

            case "stats_health" -> user != null ? Util.formatNumberWithDot(user.getHealthBar().getHealth()) : String.valueOf(0);
            case "stats_health_nf" -> user != null ? String.valueOf(user.getHealthBar().getHealth()) : String.valueOf(0);
            case "stats_health_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getHealthBar().getHealth()) : String.valueOf(0);

            case "stats_maxhealth" -> user != null ? Util.formatNumberWithDot(user.getHealthBar().getMaxHealth()) : String.valueOf(0);
            case "stats_maxhealth_nf" -> user != null ? String.valueOf(user.getHealthBar().getMaxHealth()) : String.valueOf(0);
            case "stats_maxhealth_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getHealthBar().getMaxHealth()) : String.valueOf(0);

            case "stats_healthregen" -> user != null ? Util.formatNumberWithDot(user.getHealthBar().getHealthRegen()) : String.valueOf(0);
            case "stats_healthregen_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getHealthBar().getHealthRegen()) : String.valueOf(0);

            case "stats_mana" -> user != null ? Util.formatNumberWithDot(user.getManaBar().getMana()) : String.valueOf(0);
            case "stats_mana_nf" -> user != null ? String.valueOf(user.getManaBar().getMana()) : String.valueOf(0);
            case "stats_mana_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getManaBar().getMana()) : String.valueOf(0);

            case "stats_maxmana" -> user != null ? Util.formatNumberWithDot(user.getManaBar().getMaxMana()) : String.valueOf(0);
            case "stats_maxmana_nf" -> user != null ? String.valueOf(user.getManaBar().getMaxMana()) : String.valueOf(0);
            case "stats_maxmana_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getManaBar().getMaxMana()) : String.valueOf(0);

            case "stats_manaregen" -> user != null ? Util.formatNumberWithDot(user.getManaBar().getManaRegen()) : String.valueOf(0);
            case "stats_manaregen_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getManaBar().getManaRegen()) : String.valueOf(0);

            case "stats_intelligence" -> user != null ? Util.formatNumberWithDot(user.getManaBar().getIntelligence()) : String.valueOf(0);
            case "stats_intelligence_nd" -> user != null ? Util.formatNumberWithoutDecimal(user.getManaBar().getIntelligence()) : String.valueOf(0);
            default -> null;
        };
    }

    public String replaceCustomPlaceholders(Player player, String text) {
        text = text.replace("{", "%").replace("}", "%");
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    public static String extractContentAfterMini(String placeholder) {
        Pattern pattern = Pattern.compile("mini_(.*)");
        Matcher matcher = pattern.matcher(placeholder);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
