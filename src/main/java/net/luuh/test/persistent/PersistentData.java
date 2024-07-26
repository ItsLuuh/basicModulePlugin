package net.luuh.test.persistent;

import com.google.common.collect.Maps;
import net.luuh.test.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PersistentData {

    private static final Map<String, NamespacedKey> KEYS = Maps.newHashMap();
    private static final Plugin PLUGIN = Main.getPlugin();

    private PersistentData() {
    }

    public static NamespacedKey getOrCreateKey(String key) {
        return KEYS.computeIfAbsent(key, k -> new NamespacedKey(PLUGIN, k));
    }

    public static <T, Z> void set(PersistentDataHolder dataHolder, NamespacedKey key, PersistentDataType<T, Z> dataType, Z value) {
        if (dataHolder == null) {
            PLUGIN.getLogger().warning(() -> "Tried to set " + key + " -> " + value + " to a null entity!");
            return;
        }

        dataHolder.getPersistentDataContainer().set(key, dataType, value);
        if (dataHolder instanceof TileState tileState) tileState.update();
    }

    public static <T, Z> void set(PersistentDataHolder dataHolder, String key, PersistentDataType<T, Z> dataType, Z value) {
        set(dataHolder, getOrCreateKey(key), dataType, value);
    }

    public static void remove(PersistentDataHolder dataHolder, NamespacedKey key) {
        if (dataHolder == null) return;

        dataHolder.getPersistentDataContainer().remove(key);
        if (dataHolder instanceof TileState tileState) tileState.update();
    }

    public static void remove(PersistentDataHolder dataHolder, String key) {
        remove(dataHolder, getOrCreateKey(key));
    }

    public static <T, Z> Optional<Z> get(PersistentDataHolder dataHolder, NamespacedKey key, PersistentDataType<T, Z> dataType) {
        return Optional.ofNullable(dataHolder.getPersistentDataContainer().get(key, dataType));
    }

    public static <T, Z> Optional<Z> get(PersistentDataHolder dataHolder, String key, PersistentDataType<T, Z> dataType) {
        return get(dataHolder, getOrCreateKey(key), dataType);
    }

    public static String getNamespace(ItemMeta itemMeta) {
        if (itemMeta != null && itemMeta.getPersistentDataContainer() != null) {
            for (NamespacedKey key : itemMeta.getPersistentDataContainer().getKeys()) {
                String keyString = key.getKey();
                int underscoreIndex = keyString.indexOf("_");
                if (underscoreIndex > 0) {
                    return keyString.substring(0, underscoreIndex);
                }
            }
        }
        return null;
    }

    public static void unstackable(ItemStack itemStack) {
        itemStack.editMeta(itemMeta -> set(itemMeta, "unstackable", PersistentDataType.STRING, UUID.randomUUID().toString()));
    }

    public static void unstackable(PersistentDataHolder dataHolder) {
        set(dataHolder, "unstackable", PersistentDataType.STRING, UUID.randomUUID().toString());
    }

}
