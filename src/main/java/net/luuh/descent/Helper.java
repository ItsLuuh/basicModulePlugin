package net.luuh.descent;

import net.luckperms.api.LuckPerms;
import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.descent.constants.DefaultColors;
import net.luuh.descent.database.DatabaseProvider;
import net.luuh.descent.files.MexFileManager;
import net.luuh.descent.modules.essentials.Essentials;
import net.luuh.descent.modules.itemeditor.ItemEditor;
import net.luuh.descent.modules.staff.StaffMode;
import net.luuh.descent.placeholders.PlaceholderManager;
import net.luuh.descent.players.listeners.PlayerListener;
import net.luuh.descent.players.manager.PlayerManager;
import net.luuh.descent.players.task.RequestsTask;
import net.luuh.descent.utils.RMUtils;
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

        this.mexFileManager = new MexFileManager();
        this.mexFileManager.setup(plugin);
        this.mexFileManager.saveData();

        this.rmutils = new RMUtils(mexFileManager.getMessages(), this);

        this.defaultColors = new DefaultColors(this.mexFileManager.getMessages(), this);
        this.defaultColors.load();

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
