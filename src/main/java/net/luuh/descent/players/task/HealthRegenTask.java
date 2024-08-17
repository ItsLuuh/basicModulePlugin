package net.luuh.descent.players.task;

import net.luuh.descent.Helper;
import net.luuh.descent.attributes.attributes.Health;
import net.luuh.descent.attributes.attributes.HealthRegen;
import net.luuh.descent.attributes.attributes.MaxHealth;
import net.luuh.descent.players.mana.HealthBar;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthRegenTask extends BukkitRunnable {

    private final Helper helper;

    public HealthRegenTask(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        helper.getUserManager().forEach(user -> {
            if (user == null) {
                throw new IllegalStateException("User is not initialized");
            }
            HealthBar healthBar = user.getHealthBar();
            if (healthBar == null) {
                throw new IllegalStateException("HealthBar is not initialized");
            }
            double healthRegen = user.getHealthBar().getHealthRegen();
            double maxHealth = user.getHealthBar().getMaxHealth();
            double health = user.getHealthBar().getHealth();

            if(user.getPlayer() != null && user.getPlayer().getMaxHealth() < maxHealth) user.getPlayer().setMaxHealth(maxHealth);

            if(health == maxHealth) return;
            if(healthRegen + health >= maxHealth) user.getHealthBar().setHealth(maxHealth);
            else if(health < maxHealth) user.getHealthBar().addHealth(healthRegen);
        });
    }
}