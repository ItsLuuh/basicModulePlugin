package net.luuh.descent.attributes;


import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.persistent.PersistentData;
import net.luuh.descent.players.stats.object.UserAttributes;
import net.luuh.descent.players.stats.object.UserStats;
import net.luuh.descent.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AttributeManager {

    private static final Set<Attribute<?>> attributeMap = new HashSet<>();

    public AttributeManager(){
        registerAttribute(new Strength());
        registerAttribute(new MeleeDamage());
        registerAttribute(new Range());
        registerAttribute(new AbilityDamage());
        registerAttribute(new Defense());
        registerAttribute(new Health());
        registerAttribute(new HealthRegen());
        registerAttribute(new MaxHealth());
        registerAttribute(new CritChance());
        registerAttribute(new CritDamage());
        registerAttribute(new Intelligence());
        registerAttribute(new MaxMana());
        registerAttribute(new ManaDamage());
        registerAttribute(new ManaRegen());
        registerAttribute(new Agility());
        registerAttribute(new PlayerSpeed());
        registerAttribute(new AttackSpeed());
    }

    public void registerAttribute(Attribute<Double> attribute){
        attributeMap.add(attribute);
    }

    // there are two types of attributes, stats and items
    // stats are attributes that are shown to the player, like health, defense, etc.
    // items attributes are used for example to create a key to open a gate
    public static <Z> ItemStack setItemAttribute(ItemStack item, Attribute<Z> attribute) {
        if(isAttributePresent(item, attribute)) return item;
        return new ItemBuilder(item)
                .addPersistent("attribute-" + attribute.getName(), attribute.getDataType(), attribute.getValue())
                .get();
    }

    public static Set<Attribute<?>> getAttributes(){
        return attributeMap;
    }

    public static Attribute<Double> getStatAttribute(String name){
        for(Attribute attribute : attributeMap){
            if(attribute.getName().equals(name) && attribute.getDataType().equals(PersistentDataType.DOUBLE)) return attribute;
        }
        return null;
    }

    public static <Z> boolean isAttributePresent(ItemStack item, Attribute<Z> attribute) {
        Optional<Z> value = PersistentData.get(item.getItemMeta(), "attribute-" + attribute.getName(), attribute.getDataType());
        return value.isPresent();
    }

    public static <Z> Set<Attribute<Z>> getItemAttributes(ItemStack item) {
        Set<Attribute<Z>> attributes = new HashSet<>();
        for(Attribute attribute : attributeMap){
            if(isAttributePresent(item, attribute))attributes.add(attribute);
        }
        return attributes;
    }

    public static Set<Attribute<Double>> getItemStatsAttributes(ItemStack item) {
        Set<Attribute<Double>> attributes = new HashSet<>();
        for(Attribute attribute : attributeMap){
            if(isAttributePresent(item, attribute) && attribute.getDataType().equals(PersistentDataType.DOUBLE))attributes.add(attribute);
        }
        return attributes;
    }

    public static <Z> Optional<Z> getValue(ItemStack item, Attribute<Z> attribute) {
        return PersistentData.get(item.getItemMeta(), "attribute-" + attribute.getName(), attribute.getDataType());
    }

    public static void changeAttributes(ItemStack currentItem, ItemStack cursorItem, UserAttributes userAttributes){
        if(getItemStatsAttributes(currentItem).isEmpty() && getItemStatsAttributes(cursorItem).isEmpty()) return;

        for(Attribute<Double> attribute : getItemStatsAttributes(currentItem)) {
            userAttributes.reset(attribute.getClass().getName());
        }

        for(Attribute<Double> attribute : getItemStatsAttributes(cursorItem)) {
            userAttributes.set(attribute.getClass().getName(), attribute.getValue());
        }
    }

}
