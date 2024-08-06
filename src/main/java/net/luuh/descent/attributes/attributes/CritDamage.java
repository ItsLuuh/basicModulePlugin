package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class CritDamage extends Attribute<Double>{

    protected double value;

    public CritDamage() {
        super("crit_damage","img-crit_damage", 0d, PersistentDataType.DOUBLE);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
