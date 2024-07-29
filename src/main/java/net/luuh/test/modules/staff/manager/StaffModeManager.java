package net.luuh.test.modules.staff.manager;

import net.luuh.test.utils.Serializer;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class StaffModeManager {

    HashMap<Player, Boolean> staffModeMap = new HashMap<>();
    HashMap<Player, Boolean> vanishMap = new HashMap<>();
    HashMap<Player, String> inventoryMap = new HashMap<>();
    HashMap<Inventory, String> inventoryContentMap = new HashMap<>();

    public void setInventory(Player player, Inventory inventory) {
        String hex = Serializer.serializeItems(inventory.getContents());
        inventoryMap.put(player, hex);
    }

    public ItemStack[] getItems(Player player) {
        return Serializer.deserializeItems(inventoryMap.get(player), player.getInventory().getSize());
    }

    public void applyInventory(Player player) {
        ItemStack[] content = getItems(player);
        if (content == null) return;
        for (int i = 0; i < content.length; i++) {
            if (content[i] == null) continue;
            player.getInventory().setItem(i, content[i]);
        }
    }
    
    public void setStaffMode(Player player, boolean value) {
        staffModeMap.put(player, value);
    }

    public void toggleStaffMode(Player player) {
        boolean staffMode = !isStaffMode(player);
        vanishMap.put(player, staffMode);
        if(staffMode) {
            player.setAllowFlight(true);
        }
    }
    
    public boolean isStaffMode(Player player) {
        return staffModeMap.getOrDefault(player, false);
    }
    
    public void setVanish(Player player, boolean value) {
        vanishMap.put(player, value);
    }
    
    public void toggleVanish(Player player) {
        boolean vanish = !isVanish(player);
        vanishMap.put(player, vanish);
        if(vanish) {
            player.spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 400, 0, 1, 0 , 0.25);
            player.setInvisible(true);
            player.setAllowFlight(true);
        } else {
            player.setInvisible(false);
        }
    }
    
    public boolean isVanish(Player player) {
        return vanishMap.getOrDefault(player, false);
    }

}
