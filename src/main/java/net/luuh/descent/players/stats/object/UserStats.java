package net.luuh.descent.players.stats.object;

import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.mana.HealthBar;
import net.luuh.descent.players.mana.ManaBar;
import net.luuh.descent.players.manager.UserManager;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserStats {

    private final UserManager userManager;
    private final UPT upt;
    private final Map<StatType, Double> visualStats;

    public UserStats(UserManager userManager, UPT upt, Map<StatType, Double> visualStats) {
        this.userManager = userManager;
        this.upt = upt;
        this.visualStats = visualStats;
    }

    public UPT getPlayerUPT() {
        return upt;
    }

    public double getVisual(StatType statType) {
        return visualStats.get(statType);
    }

    public CompletableFuture<Void> set(double amount, StatType statType) {
        return userManager.setValue(upt, statType, amount).thenRun(() -> visualStats.put(statType, amount));
    }

    public CompletableFuture<Double> get(StatType statType) {
        return userManager.getValue(upt, statType).thenApply(value -> {
            visualStats.put(statType, value);

            return value;
        });
    }

    public CompletableFuture<Void> add(double amount, StatType statType) {
        return userManager.addValue(upt, amount, statType)
                .thenAccept(value -> visualStats.computeIfPresent(statType, (economyType1, currentAmount) -> currentAmount + amount));
    }

    public CompletableFuture<Void> remove(double amount, StatType statType) {
        return userManager.removeValue(upt, amount, statType)
                .thenAccept(value -> visualStats.computeIfPresent(statType, (economyType1, currentAmount) -> currentAmount - amount));
    }


    public CompletableFuture<Void> reset(StatType statType) {
        return userManager.setValue(upt, statType, 0).thenRun(() -> visualStats.put(statType, 0d));
    }

    public void resetAll() {
        for(EconomyType economyType : EconomyType.values()) {
            userManager.setValue(upt, economyType, 0);
        }
    }


}
