package net.luuh.descent.players.mana;

import net.luuh.descent.Helper;
import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.entity.Player;

public class HealthBar {
    
    private final User user;
    private Double health = 1.0;

    public HealthBar(User user) {

        this.user = user;
    }

    public UPT getPlayerUPT() {
        return user.getUpt();
    }

    public Player getPlayer() {
        return user.getPlayer();
    }

    public double getHealthRegen() {
        if(user.getFinalStat(StatType.HEALTH_REGEN, HealthRegen.class) == 0) return 0;
        return user.getFinalStat(StatType.HEALTH_REGEN, HealthRegen.class);
    }

    public double getMaxHealth() {
        if(user.getFinalStat(StatType.MAX_HEALTH, MaxHealth.class) == 0) return 0;
        return user.getFinalStat(StatType.MAX_HEALTH, MaxHealth.class);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double amount) {
        this.health = amount;
        getPlayer().setHealth(amount);
    }

    public void addHealth(double amount) {
        this.health += amount;
        getPlayer().setHealth(health);
    }

    public void removeHealth(double amount) {
        this.health -= amount;
        getPlayer().setHealth(health);
    }

    public void setPlayerMaxHealth(double amount) {
        getPlayer().setHealth(amount);
    }

    public void addPlayerMaxHealth(double amount) {
        getPlayer().setHealth(getPlayer().getHealth() + amount);
    }

    public void removePlayerMaxHealth(double amount) {
        getPlayer().setHealth(getPlayer().getHealth() - amount);
    }
}
