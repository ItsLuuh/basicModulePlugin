package net.luuh.test.constants;

import net.luuh.test.Main;
import net.luuh.test.utils.RCUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class DefaultColors {

    private static final Map<String, String> colors = new HashMap<>();

    public static void load() {
        for(ConfigurationSection cs : RCUtils.readCS("defaultcolors")) {
            put(cs.getName(), cs.getString(cs.getName()));
        }
    }

    public static void put(String name, String color) {
        colors.put(name.toLowerCase(), color);
    }

    public static String get(String name) {
        return colors.get(name.toLowerCase());
    }

    public static Map<String, String> getColors() {
        return colors;
    }


}
