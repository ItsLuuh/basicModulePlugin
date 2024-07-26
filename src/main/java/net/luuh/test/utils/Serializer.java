package net.luuh.test.utils;

import org.bukkit.inventory.ItemStack;

import java.util.HexFormat;

public class Serializer {

    private static final String AIR_HEX = "air";
    private static final char SPACER = '#';

    private Serializer() {
    }

    public static String serializeItems(ItemStack[] itemStacks) {
        StringBuilder builder = new StringBuilder();

        for (ItemStack item : itemStacks)
            builder.append(item == null ? AIR_HEX : HexFormat.of().formatHex(item.serializeAsBytes())).append(SPACER);

        return builder.toString();
    }

    public static ItemStack[] deserializeItems(String string, int size) {
        String[] hexSplit = string.split(String.valueOf(SPACER));
        ItemStack[] itemStacks = new ItemStack[size];

        for (int i = 0; i < hexSplit.length; i++) {
            String hexOrNull = hexSplit[i];
            if (!hexOrNull.equals(AIR_HEX))
                itemStacks[i] = ItemStack.deserializeBytes(HexFormat.of().parseHex(hexOrNull));
        }

        return itemStacks;
    }
}
