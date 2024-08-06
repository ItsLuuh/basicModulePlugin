package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class Strength extends Attribute<Double>{

    protected double value;

    public Strength() {
        super("strength","img-strength", 0d, PersistentDataType.DOUBLE);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
