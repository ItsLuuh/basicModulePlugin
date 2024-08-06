package net.luuh.descent.modules.stats.listener;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.modules.stats.Stats;
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

            if (isArmor(currentItem) || isArmor(cursorItem)) {

                changeAttributes(currentItem, cursorItem, player);
            }

        } else if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {

            changeAttributes(inventory.getHelmet(), inventory.getHelmet(), player);
            changeAttributes(inventory.getChestplate(), inventory.getChestplate(), player);
            changeAttributes(inventory.getLeggings(), inventory.getLeggings(), player);
            changeAttributes(inventory.getBoots(), inventory.getBoots(), player);

        }
    }

    private void changeAttributes(ItemStack currentItem, ItemStack cursorItem, Player player){
        if(AttributeManager.getStatsAttributes(currentItem).isEmpty() && AttributeManager.getStatsAttributes(cursorItem).isEmpty()) return;

        UserStats userStats = helper.getPlayerManager().getUser(player).getUserStats();

        for(Attribute<Double> attribute : AttributeManager.getStatsAttributes(currentItem)) {

            userStats.remove(attribute.getValue(), attribute);
        }

        for(Attribute<Double> attribute : AttributeManager.getStatsAttributes(cursorItem)) {

            userStats.set(attribute.getValue(), attribute);
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
