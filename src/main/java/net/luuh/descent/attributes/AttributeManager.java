package net.luuh.descent.attributes;


import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.persistent.PersistentData;
import net.luuh.descent.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AttributeManager {

    private static final Map<Class<? extends Attribute>, Attribute> attributeMap = new HashMap<>();

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

    public <Z> void registerAttribute(Attribute<Z> attribute){
        attributeMap.put(attribute.getClass(), attribute);
    }

    // there are two types of attributes, stats and abilities
    // stats are attributes that are shown to the player, like health, defense, etc.
    // abilities are attributes that are used for example to create a key to open a gate
    public static <Z> ItemStack setAttribute(ItemStack item, Attribute<Z> attribute) {
        if(getItemAttribute(item, attribute)) return item;
        return new ItemBuilder(item)
                .addPersistent("attribute-" + attribute.getName(), attribute.getDataType(), attribute.getValue())
                .get();
    }

    public static Map<Class<? extends Attribute>, Attribute> getAttributes(){
        return attributeMap;
    }

    public static <Z> boolean getItemAttribute(ItemStack item, Attribute<Z> attribute) {
        Optional<Z> value = PersistentData.get(item.getItemMeta(), "attribute-" + attribute.getName(), attribute.getDataType());
        return value.isPresent();
    }

    public static <Z> Optional<Z> getValue(ItemStack item, Attribute<Z> attribute) {
        return PersistentData.get(item.getItemMeta(), "attribute-" + attribute.getName(), attribute.getDataType());
    }

    public static <Z> Set<Attribute<Z>> getItemAttributes(ItemStack item) {
        Set<Attribute<Z>> attributes = new HashSet<>();
        for(Attribute attribute : attributeMap.values()){
            if(getItemAttribute(item, attribute))attributes.add(attribute);
        }
        return attributes;
    }

    public static Set<Attribute<Double>> getStatsAttributes(ItemStack item) {
        Set<Attribute<Double>> attributes = new HashSet<>();
        for(Attribute attribute : attributeMap.values()){
            if(getItemAttribute(item, attribute) && attribute.getDataType().equals(PersistentDataType.DOUBLE))attributes.add(attribute);
        }
        return attributes;
    }



}
