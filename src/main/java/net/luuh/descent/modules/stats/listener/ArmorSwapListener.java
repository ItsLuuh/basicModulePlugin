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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorSwapListener extends ModuleListener<Stats> {

    public ArmorSwapListener(Helper helper, Stats module) {
        super(helper, module);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (isArmor(item)) {
            Player player = event.getPlayer();
            if(isHelmet(item))
                AttributeManager.changeAttributes(player.getInventory().getHelmet(), item, helper.getUserManager().getUser(player).getUserAttributes());
            else if(isChestplate(item))
                AttributeManager.changeAttributes(player.getInventory().getChestplate(), item, helper.getUserManager().getUser(player).getUserAttributes());
            else if(isLeggings(item))
                AttributeManager.changeAttributes(player.getInventory().getLeggings(), item, helper.getUserManager().getUser(player).getUserAttributes());
            else if(isBoots(item))
                AttributeManager.changeAttributes(player.getInventory().getBoots(), item, helper.getUserManager().getUser(player).getUserAttributes());
        }
    }

    private boolean isArmor(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_HELMET") || material.name().endsWith("_CHESTPLATE")
                || material.name().endsWith("_LEGGINGS") || material.name().endsWith("_BOOTS");
    }

    private boolean isHelmet(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_HELMET");
    }

    private boolean isChestplate(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_CHESTPLATE");
    }

    private boolean isLeggings(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_LEGGINGS");
    }

    private boolean isBoots(ItemStack item) {
        if (item == null) return false;
        Material material = item.getType();
        return material.name().endsWith("_BOOTS");
    }


}
