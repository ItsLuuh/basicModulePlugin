package net.luuh.descent.abstraction;

import net.luuh.descent.Helper;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class DefaultListener implements Listener {

    protected final Helper helper;

    public DefaultListener(Helper helper) {
        this.helper = helper;

        Bukkit.getServer().getPluginManager().registerEvents(this, this.helper.getPlugin());
    }

}
