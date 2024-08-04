package net.luuh.descent.utils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

public class Util {

    public static Optional<Player> playerFromName(String playerName) {
        return Optional.ofNullable(Bukkit.getPlayer(playerName));
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

    public static String formatNumberWithDot(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(number);
    }

    public static String formatNumberWithDot(BigDecimal number) {
        return formatNumberWithDot(number.doubleValue());
    }

    public static void sendPlayerSound(Player player, String sound, Sound.Source source, float volume, float pitch) {
        player.playSound(Sound.sound(Key.key(sound), source, volume, pitch));
    }

    public static void sendGlobalSound(Player player, String sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
