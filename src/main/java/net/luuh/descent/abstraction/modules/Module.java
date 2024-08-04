package net.luuh.descent.abstraction.modules;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.metadata.loader.MetadataLoader;

import java.util.HashSet;
import java.util.Set;

public abstract class Module {

    protected final Helper helper;
    protected final Set<ModuleListener<?>> listeners;
    protected final Set<ModuleCommand<?>> commands;
    protected final Set<MetadataLoader<?>> loaders;

    protected Set<ModuleListener<?>> getListeners() {
        return listeners;
    }

    protected Set<ModuleCommand<?>> getCommands() {
        return commands;
    }

    protected Set<MetadataLoader<?>> getLoaders() {
        return loaders;
    }

    public Module(Helper helper) {
        this.helper = helper;
        this.listeners = new HashSet<>();
        this.commands = new HashSet<>();
        this.loaders = new HashSet<>();
    }

    public void enable() {
        onEnable();

        this.listeners.addAll(getListeners());
        this.commands.addAll(getCommands());
        this.loaders.addAll(getLoaders());

        helper.registerLoaders(loaders);

        helper.getPlugin().getLogger().info("#" + getClass().getSimpleName().toLowerCase() + " enabled");
    }

    public void disable() {
        onDisable();
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

}
