package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class ManaRegen extends Attribute<Double>{

    protected double value;

    public ManaRegen() {
        super("mana_regen","img-mana_regen", 0d, PersistentDataType.DOUBLE);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}