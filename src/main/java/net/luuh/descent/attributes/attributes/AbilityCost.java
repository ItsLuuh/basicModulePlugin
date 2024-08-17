package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class AbilityCost extends Attribute<Double>{

    protected double value;

    public AbilityCost() {
        super("ability_damage","img-ability_damage", 0d, PersistentDataType.DOUBLE);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
