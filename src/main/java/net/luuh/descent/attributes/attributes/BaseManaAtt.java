package net.luuh.descent.attributes.attributes;

import net.luuh.descent.attributes.Attribute;
import org.bukkit.persistence.PersistentDataType;

public class BaseManaAtt extends Attribute<String, Integer>{

    protected int value;

    protected BaseManaAtt(PersistentDataType<String, Integer> dataType, int value) {
        super("base-mana","img-customtags:base-mana-attribute", value, dataType);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
