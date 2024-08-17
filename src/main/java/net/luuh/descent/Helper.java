package net.luuh.descent;

import net.luckperms.api.LuckPerms;
import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.descent.database.DatabaseProvider;
import net.luuh.descent.files.MexFileManager;
import net.luuh.descent.modules.essentials.Essentials;
import net.luuh.descent.modules.itemeditor.ItemEditor;
import net.luuh.descent.modules.staff.StaffMode;
import net.luuh.descent.modules.stats.Stats;
import net.luuh.descent.placeholders.PAPIHook;
import net.luuh.descent.players.listeners.PlayerListener;
import net.luuh.descent.players.manager.UserManager;
import net.luuh.descent.players.task.HealthRegenTask;
import net.luuh.descent.players.task.ManaRegenTask;
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
    private final UserManager userManager;
    private final Scheduler scheduler;
    //private final PlaceholderManager papi;
    private LuckPerms luckPerms = null;
    private final MexFileManager mexFileManager;
    private RMUtils rmutils;
    private final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();
    private final Set<MetadataLoader<?>> loaders = new HashSet<>();

    public Helper(Main plugin) {
        this.plugin = plugin;

        this.scheduler = new Scheduler(plugin);

        this.databaseProvider = new DatabaseProvider(plugin);
        this.databaseProvider.assemble();

        this.userManager = new UserManager(this);

        this.mexFileManager = new MexFileManager();
        this.mexFileManager.setup(plugin);
        this.mexFileManager.saveData();

        this.rmutils = new RMUtils(mexFileManager.getMessages(), this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

        new PAPIHook(this).register();

        Essentials essentials = new Essentials(this);
        ItemEditor itemEditor = new ItemEditor(this);
        StaffMode staffMode = new StaffMode(this);
        Stats stats = new Stats(this);

        registerModules(
                essentials,
                itemEditor,
                staffMode,
                stats

        );


        this.moduleMap.values().forEach(Module::enable);

        // this.papi = new PlaceholderManager(this);

        new PlayerListener(this);
        this.userManager.loadOnline().join();

        this.scheduler.timerAsync(new RequestsTask(this), 0, 20);
        this.scheduler.timerAsync(new ManaRegenTask(this), 0, 20);
        this.scheduler.timer(new HealthRegenTask(this), 0, 20);

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

    public UserManager getUserManager() {
        return userManager;
    }

    //public PlaceholderManager getPlaceholderManager() {return papi;}

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

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
