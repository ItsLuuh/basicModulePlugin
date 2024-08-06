package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class AbilityDamage extends Attribute<Double>{

    protected double value;

    public AbilityDamage() {
        super("ability_damage","img-ability_damage", 0d, PersistentDataType.DOUBLE);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
