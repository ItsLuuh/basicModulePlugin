package net.luuh.test.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.luuh.test.persistent.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack.clone();
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder editMeta(Consumer<ItemMeta> consumer) {
        item.editMeta(consumer);
        return this;
    }

    public ItemBuilder headTexture(String texture) {
        editMeta(itemMeta -> {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URL("https://textures.minecraft.net/texture/" + texture));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            skullMeta.setOwnerProfile(profile);
        });

        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        editMeta(itemMeta -> {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        });

        return this;
    }

    public ItemBuilder setName(String name) {
        item.editMeta(itemMeta -> itemMeta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false)));
        return this;
    }

    public ItemBuilder setName(Component component) {
        item.editMeta(itemMeta -> itemMeta.displayName(component.decoration(TextDecoration.ITALIC, false)));
        return this;
    }

    public ItemBuilder setLore(Component... components) {
        item.editMeta(itemMeta -> itemMeta.lore(List.of(Arrays.stream(components).map(component -> component.decoration(TextDecoration.ITALIC, false)).toArray(Component[]::new))));
        return this;
    }

    public <T, Z> ItemBuilder addPersistent(String key, PersistentDataType<T, Z> type, Z value) {
        item.editMeta(meta -> PersistentData.set(meta, key, type, value));

        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        item.editMeta(itemMeta -> itemMeta.setCustomModelData(customModelData));

        return this;
    }

    public ItemBuilder setEnchantments(List<Enchantment> enchantments, int lvl) {
        item.editMeta(itemMeta -> enchantments.forEach(enchantment -> itemMeta.addEnchant(enchantment, lvl, true)));

        return this;
    }


    public ItemStack get() {
        return item.clone();
    }
}
