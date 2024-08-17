package net.luuh.descent.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luuh.descent.Main;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class color {

    public static Component format(String message) {
        return  MiniMessage.miniMessage().deserialize(message);
    }

    @NotNull
    private static String getString(String message, Pattern pattern) {
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String formatLegacy(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        return getString(message, pattern);
    }

    public static String formatStringComponent(String message) {
        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        return getString(message, pattern);
    }

    public static TextComponent getComp(String message){
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static String getLegacy(Component message){
        return LegacyComponentSerializer.legacyAmpersand().serialize(message);
    }

    public static String getPlain(Component message){
        return PlainTextComponentSerializer.plainText().serialize(message);
    }

    public static String getPlain(String message) {
        return message.replaceAll("§[a-zA-Z0-9]", "");
    }


}
