package net.luuh.test.utils;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.kyori.adventure.text.Component;
import net.luuh.test.Helper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RMUtils {

    private final Helper helper;

    public RMUtils(FileConfiguration config, Helper helper) {
        this.config = config;
        this.helper = helper;

    }
    FileConfiguration config;

    @NotNull
    private Component getComponentFromList(String path) {
        if (config.isList(path)) {
            List<Component> components = new ArrayList<>();
            for (String s : readList(path)) {
                components.add(color.format(
                        helper.getPlaceholderManager().replace(null,
                                FontImageWrapper.replaceFontImages(
                                        color.replaceImage(
                                                s
                                        )
                                )
                        )
                ).appendNewline());
            }
            return Component.text().append(components).build();
        }
        else return Component.text().build();
    }
    @NotNull
    private Component getComponentFromList(Player player, String path) {
        if (config.isList(path)) {
            List<Component> components = new ArrayList<>();
            for (String s : readList(path)) {
                components.add(color.format(
                        helper.getPlaceholderManager().replace(player,
                                FontImageWrapper.replaceFontImages(
                                        color.replaceImage(
                                                s
                                        )
                                )
                        )
                ).appendNewline());
            }
            return Component.text().append(components).build();
        }
        else return Component.text().build();
    }

    public String replaceArgs(String s, Map<String, String> args) {
        for (Map.Entry<String, String> entry : args.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }
        return s;
    }


    public Component readTranslation(String path) {
        if(isString(path)) return color.format(
                helper.getPlaceholderManager().replace(null,
                        FontImageWrapper.replaceFontImages(
                                color.replaceImage(
                                        readString(path)
                                )
                        )
                )
        );
        else return getComponentFromList(path);
    }

    public Component readTranslation(Player player, String path) {
        if(isString(path)) return color.format(
                helper.getPlaceholderManager().replace(
                        player,
                        color.getPlain(FontImageWrapper.replaceFontImages(
                                color.replaceImage(
                                        color.formatCPH(
                                                readString(path)
                                        )
                                )
                        ))
                )
        );
        else return getComponentFromList(player, path);
    }

    public String readTranslationString(Player player, String path) {
        if (isString(path)) {

            return color.formatStringComponent(helper.getPlaceholderManager().replace(player, readString(path)));
        } else if (config.isList(path)) {

            StringBuilder formattedStringBuilder = new StringBuilder();
            for (String s : readList(path)) {

                s = helper.getPlaceholderManager().replace(player, s);
                formattedStringBuilder.append(color.format(s)).append("\n");
            }

            return formattedStringBuilder.toString();
        } else {

            return "";
        }
    }

    public String readTranslationString(String path) {
        if (isString(path)) {

            return color.formatStringComponent(readString(path));
        } else if (config.isList(path)) {

            StringBuilder formattedStringBuilder = new StringBuilder();
            for (String s : readList(path)) {

                formattedStringBuilder.append(color.format(s)).append("\n");
            }

            return formattedStringBuilder.toString();
        } else {

            return "";
        }
    }

    public List<ConfigurationSection> readCS(String path) {
        List<ConfigurationSection> csList = new ArrayList<>();
        for(String s : config.getConfigurationSection(path).getKeys(false)) {
            csList.add(config.getConfigurationSection(s));
        }
        return csList;
    }

    public List<String> readList(String path) {
        return config.getStringList(path);
    }

    public boolean readBool(String path) {
        return config.getBoolean(path);
    }

    public String readString(String path) {
        return config.getString(path);
    }

    public int readInt(String path) {
        return config.getInt(path);
    }

    public double readDouble(String path) {
        return config.getDouble(path);
    }

    public boolean isString(String path) {
        if (config == null || !config.contains(path)) {
            return false;
        }
        return config.isString(path);
    }

    public boolean isStringList(String path) {
        if (config == null || !config.contains(path)) {
            return false;
        }
        return config.isList(path) && config.getList(path).stream().allMatch(obj -> obj instanceof String);
    }

}
