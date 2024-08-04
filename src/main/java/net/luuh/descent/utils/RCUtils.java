package net.luuh.descent.utils;

import net.luuh.descent.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class RCUtils {

    private static final Main plugin = Main.getPlugin();

    public static List<ConfigurationSection> readCS(String path) {
        List<ConfigurationSection> csList = new ArrayList<>();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(path);
        if (section != null) {
            for(String s : section.getKeys(true)) {
                csList.add(plugin.getConfig().getConfigurationSection(s));
            }
        }
        return csList;
    }

    public static List<String> readList(String path) {
        return plugin.getConfig().getStringList(path);
    }

    public static boolean readBool(String path) {
        return plugin.getConfig().getBoolean(path);
    }

    public static String readString(String path) {
        return plugin.getConfig().getString(path);
    }

    public static int readInt(String path) {
        return plugin.getConfig().getInt(path);
    }

    public static double readDouble(String path) {
        return plugin.getConfig().getDouble(path);
    }

    public void set(String path, Object value) {
        plugin.getConfig().set(path, value);
        plugin.saveConfig();
    }

}
