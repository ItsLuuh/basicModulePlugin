package net.luuh.test;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private Helper helper;
    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.helper = new Helper(this);

    }

    @Override
    public void onDisable() {
        this.helper.disable();
    }

    public static Main getPlugin() {
        return plugin;
    }
    public Helper getHelper() {
        return helper;
    }
}
