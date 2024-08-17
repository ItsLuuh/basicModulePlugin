package net.luuh.descent.modules.stats.listener;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.modules.stats.Stats;
import net.luuh.descent.players.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamagedEvent extends ModuleListener<Stats> {
    public PlayerDamagedEvent(Helper helper, Stats module) {
        super(helper, module);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            User user = helper.getUserManager().getUser(player);
            user.getHealthBar().removeHealth(event.getDamage());
        }
    }
}
