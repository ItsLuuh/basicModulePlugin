package net.luuh.test.players.economy.object;


import net.luuh.test.players.economy.constant.EconomyType;
import net.luuh.test.players.manager.PlayerManager;
import net.luuh.test.players.objects.UPT;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserEconomy {

    private final PlayerManager playerManager;
    private final UPT upt;
    private final Map<EconomyType, BigDecimal> visualBalances;

    public UserEconomy(PlayerManager playerManager, UPT upt, Map<EconomyType, BigDecimal> visualBalances) {
        this.playerManager = playerManager;
        this.upt = upt;
        this.visualBalances = visualBalances;
    }

    public double getVisual(EconomyType economyType) {
        return visualBalances.get(economyType).doubleValue();
    }

    public CompletableFuture<Void> set(double amount, EconomyType economyType) {
        return playerManager.setValue(upt, economyType, amount).thenRun(() -> visualBalances.put(economyType, BigDecimal.valueOf(amount)));
    }

    public CompletableFuture<Double> get(EconomyType economyType) {
        return playerManager.getValue(upt, economyType).thenApply(value -> {
            visualBalances.put(economyType, BigDecimal.valueOf(value));

            return value;
        });
    }

    public CompletableFuture<Void> add(double amount, EconomyType economyType) {
        return playerManager.addValue(upt, amount, economyType)
                .thenAccept(value -> visualBalances.computeIfPresent(economyType, (economyType1, bigDecimal) -> bigDecimal.add(BigDecimal.valueOf(amount))));
    }

    public CompletableFuture<Void> remove(double amount, EconomyType economyType) {
        return playerManager.removeValue(upt, amount, economyType)
                .thenAccept(value -> visualBalances.computeIfPresent(economyType, (economyType1, bigDecimal) -> bigDecimal.subtract(BigDecimal.valueOf(amount))));
    }
}
