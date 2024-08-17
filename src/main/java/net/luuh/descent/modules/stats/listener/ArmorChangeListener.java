package net.luuh.descent.modules.stats.listener;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.modules.stats.Stats;
import net.luuh.descent.players.stats.object.UserAttributes;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmorChangeListener extends ModuleListener<Stats> {

    public ArmorChangeListener(Helper helper, Stats module) {
        super(helper, module);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        PlayerInventory inventory = event.getWhoClicked().getInventory();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if (isArmorSlot(event.getSlot())) {

            UserAttributes userAttributes = helper.getUserManager().getUser(player).getUserAttributes();

            if (isArmor(currentItem) || isArmor(cursorItem)) {

                AttributeManager.changeAttributes(currentItem, cursorItem, userAttributes);
            }

        } else if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {

            UserAttributes userAttributes = helper.getUserManager().getUser(player).getUserAttributes();
            AttributeManager.changeAttributes(inventory.getHelmet(), inventory.getHelmet(), userAttributes);
            AttributeManager.changeAttributes(inventory.getChestplate(), inventory.getChestplate(), userAttributes);
            AttributeManager.changeAttributes(inventory.getLeggings(), inventory.getLeggings(), userAttributes);
            AttributeManager.changeAttributes(inventory.getBoots(), inventory.getBoots(), userAttributes);

        }
    }



    private boolean isArmorSlot(int slot) {
        return slot >= 5 && slot <= 8;
    }

    private boolean isArmor(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_HELMET") || material.name().endsWith("_CHESTPLATE")
                || material.name().endsWith("_LEGGINGS") || material.name().endsWith("_BOOTS");
    }
}
