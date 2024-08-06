package net.luuh.descent.players.task;

import net.luuh.descent.Helper;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.Health;
import net.luuh.descent.attributes.attributes.HealthRegen;
import net.luuh.descent.attributes.attributes.MaxHealth;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;

public class HealthRegenTask extends BukkitRunnable {

    private final Helper helper;

    public HealthRegenTask(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        helper.getPlayerManager().forEach(user -> {
            double healthRegen = 0;
            double maxHealth = 0;
            double health = 0;
            try {
                HealthRegen healthRegenAtt = (HealthRegen) AttributeManager.getAttributes().get(HealthRegen.class);
                Health healthAtt = (Health) AttributeManager.getAttributes().get(Health.class);
                MaxHealth maxHealthAtt = (MaxHealth) AttributeManager.getAttributes().get(MaxHealth.class);
                healthRegen = user.getUserStats().get(StatType.HEALTH_REGEN).get();
                healthRegen += user.getUserStats().get(healthRegenAtt);
                maxHealth = user.getUserStats().get(StatType.MAX_HEALTH).get();
                maxHealth += user.getUserStats().get(maxHealthAtt);
                health = user.getUserStats().get(healthAtt);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if(healthRegen > maxHealth)return;
            EntityRegainHealthEvent event = new EntityRegainHealthEvent(user.getPlayer(), healthRegen, EntityRegainHealthEvent.RegainReason.CUSTOM);
            if(health < maxHealth) Bukkit.getPluginManager().callEvent(event);
        });
    }
}