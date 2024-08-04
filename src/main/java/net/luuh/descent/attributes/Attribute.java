package net.luuh.descent.attributes;

import org.bukkit.persistence.PersistentDataType;

public abstract class Attribute<T, Z> {

    protected final String name;
    protected final String icon;
    protected final Z value;
    protected final PersistentDataType<T, Z> dataType;

    protected Attribute(String name, String icon, Z value, PersistentDataType<T, Z> dataType) {
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

    public PersistentDataType<T, Z> getDataType() {
        return dataType;
    }

    public String getIcon() {
        return icon;
    }
}
