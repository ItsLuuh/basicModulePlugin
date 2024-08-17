package net.luuh.descent.modules.remover.listeners;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.modules.remover.Remover;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class PhantomSpawnEvent extends ModuleListener<Remover> {
    public PhantomSpawnEvent(Helper helper, Remover module) {
        super(helper, module);
    }

    @EventHandler
    public void onPhantomSpawn(EntitySpawnEvent event) {
        if(event.getEntity() instanceof Phantom) {
            event.setCancelled(true);
        }
    }
}

