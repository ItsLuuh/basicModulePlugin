package net.luuh.descent.attributes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class Attribute<Z> {

    protected final String name;
    protected final String icon;
    protected final Z value;
    protected final PersistentDataType dataType;

    protected Attribute(String name, String icon, Z value, PersistentDataType dataType) {
        this.name = name;
        this.icon = icon;
        this.value = value;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public Z getValue() {
        return value;
    }

    public PersistentDataType getDataType() {
        return dataType;
    }

    public String getIcon() {
        return icon;
    }
}
