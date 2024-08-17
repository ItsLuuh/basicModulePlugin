package net.luuh.descent.players.stats.object;

import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.manager.UserManager;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserAttributes {

    private final UPT upt;
    private final Map<Class<?>, Double> attributes = new HashMap<>();;

    public UserAttributes(UPT upt) {
        this.upt = upt;
    }

    public void load(Player player) {
        Set<ItemStack> itemAttributes = new HashSet<>(Arrays.asList(player.getInventory().getArmorContents()));
        itemAttributes.add(player.getInventory().getItemInMainHand());
        itemAttributes.add(player.getInventory().getItemInOffHand());

        for (ItemStack itemStack : itemAttributes) {
            if (itemStack == null) continue;

            for (Attribute<Double> attribute : AttributeManager.getItemStatsAttributes(itemStack)) {
                attributes.put(attribute.getClass(), attribute.getValue());
            }
        }
    }

    public UPT getPlayerUPT() {
        return upt;
    }

    public void set(Class<? extends Attribute<Double>> attributeClass, double amount) {
        if(get(attributeClass).isEmpty()) return;
        attributes.put(attributeClass, amount);
    }

    public void set(String attributeName, double amount) {
        if(get(attributeName).isEmpty()) return;
        attributes.put(AttributeManager.getStatAttribute(attributeName).getClass(), amount);
    }

    public Optional<Double> get(Class<? extends Attribute<Double>> attributeClass) {
        return Optional.ofNullable(attributes.get(attributeClass));
    }

    public Optional<Double> get(String attributename) {
        return attributes.get(AttributeManager.getStatAttribute(attributename).getClass()) == null ? Optional.empty() : Optional.of(attributes.get(AttributeManager.getStatAttribute(attributename).getClass()));
    }

    public void add(Class<? extends Attribute<Double>> attributeClass, double amount) {
        if(get(attributeClass).isEmpty()) return;
        attributes.put(attributeClass, AttributeManager.getStatAttribute(attributeClass.getName()).getValue() + amount);
    }

    public void add(String attributeName, double amount) {
        if(get(attributeName).isEmpty()) return;
        attributes.put(AttributeManager.getStatAttribute(attributeName).getClass(), AttributeManager.getStatAttribute(attributeName).getValue() + amount);
    }

    public void remove(Class<? extends Attribute<Double>> attributeClass, double amount) {
        if(get(attributeClass).isEmpty()) return;
        attributes.put(attributeClass, AttributeManager.getStatAttribute(attributeClass.getName()).getValue() - amount);
    }

    public void remove(String attributeName, double amount) {
        if(get(attributeName).isEmpty()) return;
        attributes.put(AttributeManager.getStatAttribute(attributeName).getClass(), AttributeManager.getStatAttribute(attributeName).getValue() - amount);
    }

    public void reset(Class<? extends Attribute<Double>> attributeClass) {
        if(get(attributeClass).isEmpty()) return;
        attributes.put(attributeClass, 0d);
    }

    public void reset(String attributeName) {
        if(get(attributeName).isEmpty()) return;
        attributes.put(AttributeManager.getStatAttribute(attributeName).getClass(), 0d);
    }

    public void resetAll() {
        attributes.clear();
    }
}