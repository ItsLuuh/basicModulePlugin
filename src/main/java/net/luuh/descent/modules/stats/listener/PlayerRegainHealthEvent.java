package net.luuh.descent.modules.stats.listener;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.HealthRegen;
import net.luuh.descent.modules.stats.Stats;
import net.luuh.descent.players.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerRegainHealthEvent extends ModuleListener<Stats> {
    public PlayerRegainHealthEvent(Helper helper, Stats module) {
        super(helper, module);
    }

    @EventHandler
    public void onRegain(EntityRegainHealthEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(event.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM) event.setCancelled(true);
            User user = helper.getPlayerManager().getUser(player);
            HealthRegen healthRegenAtt = (HealthRegen) AttributeManager.getAttributes().get(HealthRegen.class);
            double healthRegen = user.getUserStats().get(healthRegenAtt);
            event.setAmount(healthRegen);
        }
    }
}
