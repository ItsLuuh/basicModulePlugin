package net.luuh.descent.modules.remover.listeners;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.modules.remover.Remover;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener extends ModuleListener<Remover> {
    public FoodChangeListener(Helper helper, Remover module) {
        super(helper, module);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}
