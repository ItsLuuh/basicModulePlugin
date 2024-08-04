package net.luuh.descent.constants;

import net.luuh.descent.Helper;
import net.luuh.descent.utils.RMUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class DefaultColors extends RMUtils {

    private final Set<Palette> colors = new HashSet<>();

    public DefaultColors(FileConfiguration config, Helper helper) {
        super(config, helper);
    }

    public void load() {
        helper.getRMUtils().getCSChildren("defaultcolors").forEach(this::put);
    }

    public void put(String name, String color) {
        colors.add(new Palette(name.toLowerCase(), helper.getRMUtils().readString(color)));
    }

    public String getColor(String name) {
        return colors.stream().filter(palette -> palette.getName().equals(name.toLowerCase())).findFirst().map(Palette::getColor).orElse(null);
    }

    public Set<Palette> getColors() {
        return colors;
    }


}
