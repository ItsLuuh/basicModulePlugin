package net.luuh.test.constants;

import net.luuh.test.utils.RCUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

public class DefaultColors {

    private final Set<Palette> colors = new HashSet<>();

    public void load() {
        for(ConfigurationSection cs : RCUtils.readCS("defaultcolors")) {
            put(cs.getName(), cs.getString(cs.getName()));
        }
    }

    public void put(String name, String color) {
        colors.add(new Palette(name.toLowerCase(), color));
    }

    public String get(String name) {
        return colors.stream().filter(palette -> palette.getName().equals(name.toLowerCase())).findFirst().map(Palette::getColor).orElse(null);
    }

    public Set<Palette> getColors() {
        return colors;
    }


}
