package net.luuh.test.abstraction.modules;

import net.kyori.adventure.sound.Sound;
import net.luuh.test.Helper;
import net.luuh.test.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModuleCommand<T extends Module> implements TabExecutor {

    protected final T module;
    protected final Helper helper;
    private final String name;
    private final List<String> aliases;

    public ModuleCommand(Helper helper, T module, String name, String... aliases) {
        this.helper = helper;
        this.module = module;
        this.name = name;
        this.aliases = Arrays.stream(aliases).toList();

        PluginCommand pluginCommand = helper.getPlugin().getCommand(name);
        if (pluginCommand == null) {
            try{
                CommandMap commandMap = (CommandMap) getPrivateField(Bukkit.getPluginManager());
                Constructor<PluginCommand> commandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                commandConstructor.setAccessible(true);
                pluginCommand = commandConstructor.newInstance(name, helper.getPlugin());
                pluginCommand.setAliases(this.aliases);

                if (!commandMap.register(helper.getPlugin().getName(), pluginCommand)) {
                    throw new RuntimeException("unable to register a registered command");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                helper.log("unable to register command /" + name);
                return;
            }
        }

        pluginCommand.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        try {
            execute(player, args);
        } catch (CommandSuccessException e) {
            if(e.isSuccess()) Util.sendPlayerSound(player, "entity.experience_orb.pickup", Sound.Source.AMBIENT, 1, 0.1f);
            else Util.sendPlayerSound(player, "entity.villager.no", Sound.Source.AMBIENT, 1, 1);
        }
        return true;
    }

    protected abstract void execute(Player player, String[] args) throws CommandSuccessException;

    protected Map<Integer, List<String>> tabComplete() {return new HashMap<>();}

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Map<Integer, List<String>> tabComplete = tabComplete();
        return tabComplete.getOrDefault(args.length, List.of());
    }

    private Object getPrivateField(Object object) throws Exception {
        Field f = object.getClass().getDeclaredField("commandMap");
        f.setAccessible(true);
        return f.get(object);
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }


}
