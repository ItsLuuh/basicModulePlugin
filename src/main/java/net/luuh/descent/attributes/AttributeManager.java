package net.luuh.descent.attributes;


import net.luuh.descent.Helper;
import net.luuh.descent.persistent.PersistentData;
import net.luuh.descent.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class AttributeManager {

    public static <T, Z> ItemStack createValue(ItemStack item, String key, PersistentDataType<T, Z> type, Z value) {
        return new ItemBuilder(item)
                .addPersistent(key, type, value)
                .get();
    }

    public static ItemStack setAttribute(ItemStack item, Attribute attribute){
        if(getAttribute(item, attribute)) return item;
        return createValue(item, attribute.getName(), attribute.getDataType(), attribute.getValue());
    }

    public static <T, Z> boolean getAttribute(ItemStack item, Attribute<T, Z> attribute) {
        Optional<Z> value = PersistentData.get(item.getItemMeta(), "attribute-" + attribute.getName(), attribute.getDataType());
        return value.isPresent();
    }



}
