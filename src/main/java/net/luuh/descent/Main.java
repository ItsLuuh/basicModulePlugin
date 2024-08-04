package net.luuh.descent;

import net.luuh.descent.utils.color;
import org.bukkit.Bukkit;
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

        log("<#8B00CD><bold>[<white>!</white><#8B00CD>]<reset> %SUCCESS%Descent has been enabled! <dark_gray>(<gray>v%server_coreversion%<reset><dark_gray>)<reset>");
        log("");
        log("<#8B00CD>    ___   __  ______  _______   <reset><white>__________  ____  ______<reset>");
        log("<#8B00CD>   /   | / / / / __ \\/  _/   | <reset><white>/ ____/ __ \\/ __ \\/ ____/<reset>");
        log("<#8B00CD>  / /| |/ / / / / / // // /| |<reset><white>/ /   / / / / /_/ / __/<reset>   ");
        log("<#8B00CD> / ___ / /_/ / /_/ // // ___ |<reset><white> /___/ /_/ / _, _/ /___<reset>   ");
        log("<#8B00CD>/_/  |_/____/_____/___/_/  |_/<reset><white>\\____/\\____/_/ |_/_____/<reset>   ");
        log("                                                        ");

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

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(color.format(message));
    }
}
