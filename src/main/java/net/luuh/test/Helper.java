package net.luuh.test;

import net.luckperms.api.LuckPerms;
import net.luuh.test.abstraction.modules.Module;
import net.luuh.test.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.test.constants.DefaultColors;
import net.luuh.test.database.DatabaseProvider;
import net.luuh.test.files.MexFileManager;
import net.luuh.test.modules.essentials.Essentials;
import net.luuh.test.modules.itemeditor.ItemEditor;
import net.luuh.test.modules.staff.StaffMode;
import net.luuh.test.placeholders.PlaceholderManager;
import net.luuh.test.players.listeners.PlayerListener;
import net.luuh.test.players.manager.PlayerManager;
import net.luuh.test.players.task.RequestsTask;
import net.luuh.test.utils.RMUtils;
import net.luuh.test.utils.color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Helper {

    private final Main plugin;
    private final DatabaseProvider databaseProvider;
    private final PlayerManager playerManager;
    private final Scheduler scheduler;
    private final PlaceholderManager papi;
    private LuckPerms luckPerms = null;
    private final MexFileManager mexFileManager;
    private RMUtils rmutils;
    private final DefaultColors defaultColors;
    private final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();
    private final Set<MetadataLoader<?>> loaders = new HashSet<>();

    public Helper(Main plugin) {
        this.plugin = plugin;

        this.scheduler = new Scheduler(plugin);

        this.databaseProvider = new DatabaseProvider(plugin);
        this.databaseProvider.assemble();

        this.playerManager = new PlayerManager(this);
        this.playerManager.loadOnline().join();

        this.defaultColors = new DefaultColors();
        this.defaultColors.load();

        this.mexFileManager = new MexFileManager();
        this.mexFileManager.setup(plugin);
        this.rmutils = new RMUtils(mexFileManager.getMessages(), this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

        this.scheduler.timerAsync(new RequestsTask(this), 0, 20);

        Essentials essentials = new Essentials(this);
        ItemEditor itemEditor = new ItemEditor(this);
        StaffMode staffMode = new StaffMode(this);

        registerModules(
                essentials,
                itemEditor,
                staffMode

        );


        this.moduleMap.values().forEach(Module::enable);

        this.papi = new PlaceholderManager(this);

        new PlayerListener(this);

    }

    public void log(String message) {
        message = color.formatStringComponent(message);
        Bukkit.getConsoleSender().sendMessage(message);
    }

    private void registerModule(Module module) {this.moduleMap.put(module.getClass(), module);}
    private void registerModules(Module... modules) {
        for (Module module : modules)
            registerModule(module);
    }

    public void disable() {
        this.mexFileManager.saveData();

        this.moduleMap.values().forEach(Module::disable);

        for(Player onlinePlayer : Bukkit.getOnlinePlayers())onlinePlayer.closeInventory();

        this.databaseProvider.disassemble();

    }

    public void reloadConfig(int i) {
        switch (i) {
            default -> {
                plugin.reloadConfig();
                mexFileManager.reloadData();
            }
            case 1 -> plugin.reloadConfig();
            case 2 -> mexFileManager.reloadData();
        }
        this.rmutils = new RMUtils(mexFileManager.getMessages(), this);
    }

    public Main getPlugin() {
        return plugin;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlaceholderManager getPlaceholderManager() {return papi;}

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public DefaultColors getDefaultColors() {return defaultColors;}

    public MexFileManager getMexFileManager() {return mexFileManager;}

    public RMUtils getRMUtils() {
        return rmutils;
    }

    public Map<Class<? extends Module>, Module> getModules() {
        return moduleMap;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return clazz.cast(moduleMap.get(clazz));
    }

    public Set<MetadataLoader<?>> getLoaders() {
        return loaders;
    }

    public void registerLoaders(Set<MetadataLoader<?>> loaders) {
        this.loaders.addAll(loaders);
    }

}
