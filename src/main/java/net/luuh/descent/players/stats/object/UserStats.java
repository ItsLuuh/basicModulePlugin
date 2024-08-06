package net.luuh.descent.players.stats.object;

import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.mana.ManaBar;
import net.luuh.descent.players.manager.PlayerManager;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserStats {

    private final PlayerManager playerManager;
    private final UPT upt;
    private final Map<StatType, Double> visualStats;
    private final ManaBar manaBar = new ManaBar(this);
    private Map<Attribute<?>, Object> attributes = new HashMap<>();

    public UserStats(PlayerManager playerManager, UPT upt, Map<StatType, Double> visualStats) {
        this.playerManager = playerManager;
        this.upt = upt;
        this.visualStats = visualStats;
    }

    public void loadAttributes(Player player) {
        Set<ItemStack> itemAttributes = new HashSet<>(Set.of());
        itemAttributes.addAll(Arrays.asList(player.getInventory().getArmorContents()));
        itemAttributes.add(player.getInventory().getItemInMainHand());
        itemAttributes.add(player.getInventory().getItemInOffHand());

        for(ItemStack itemStack : itemAttributes) {
            if(itemStack == null) continue;

            for(Attribute<Double> attribute : AttributeManager.getAttributes().values()) {
                attributes.put(attribute, attribute.getValue());

            }
        }
    }

    public UPT getPlayerUPT() {
        return upt;
    }

    public double getVisual(StatType statType) {
        return visualStats.get(statType);
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public CompletableFuture<Void> set(double amount, StatType statType) {
        return playerManager.setValue(upt, statType, amount).thenRun(() -> visualStats.put(statType, amount));
    }

    public <Z> void set(double amount, Attribute<Z> attribute) {
        attributes.put(attribute, amount);
    }

    public CompletableFuture<Double> get(StatType statType) {
        return playerManager.getValue(upt, statType).thenApply(value -> {
            visualStats.put(statType, value);

            return value;
        });
    }

    public <Z> double get(Attribute<Z> attribute) {
        return (double) attributes.get(attribute);
    }

    public <Z> double get(Attribute<Z> attribute, StatType statType) {
        return (double) attributes.get(attribute) + visualStats.get(statType);
    }

    public CompletableFuture<Void> add(double amount, StatType statType) {
        return playerManager.addValue(upt, amount, statType)
                .thenAccept(value -> visualStats.computeIfPresent(statType, (economyType1, currentAmount) -> currentAmount + amount));
    }

    public <Z> void add(double amount, Attribute<Z> attribute) {
        attributes.put(attribute, (double) attributes.get(attribute) + amount);
    }

    public CompletableFuture<Void> remove(double amount, StatType statType) {
        return playerManager.removeValue(upt, amount, statType)
                .thenAccept(value -> visualStats.computeIfPresent(statType, (economyType1, currentAmount) -> currentAmount - amount));
    }

    public <Z> void remove(double amount, Attribute<Z> attribute) {
        attributes.put(attribute, (double) attributes.get(attribute) - amount);
    }

    public CompletableFuture<Void> reset(StatType statType) {
        return playerManager.setValue(upt, statType, 0).thenRun(() -> visualStats.put(statType, 0d));
    }

    public <Z> void reset(Attribute<Z> attribute) {
        attributes.put(attribute, 0d);
    }

    public void resetAll() {
        for(EconomyType economyType : EconomyType.values()) {
            playerManager.setValue(upt, economyType, 0);
        }
    }

    public void resetAllAttributes() {
        attributes.replaceAll((attribute, currentAmount) -> 0d);
    }



}
