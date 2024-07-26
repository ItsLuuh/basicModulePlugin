package net.luuh.test.abstraction;

import net.luuh.test.Helper;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class DefaultListener implements Listener {

    protected final Helper helper;

    public DefaultListener(Helper helper) {
        this.helper = helper;

        Bukkit.getServer().getPluginManager().registerEvents(this, this.helper.getPlugin());
    }

}
