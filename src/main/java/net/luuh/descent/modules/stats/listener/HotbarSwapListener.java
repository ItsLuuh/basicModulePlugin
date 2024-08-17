package net.luuh.descent.modules.stats.listener;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.attributes.Attribute;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.modules.stats.Stats;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class HotbarSwapListener extends ModuleListener<Stats> {
    public HotbarSwapListener(Helper helper, Stats module) {
        super(helper, module);
    }

    @EventHandler
    public void onMainItemChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack previousitem = player.getInventory().getItem( event.getPreviousSlot() );
        ItemStack newitem = player.getInventory().getItem( event.getNewSlot() );

        AttributeManager.changeAttributes(previousitem, newitem, helper.getUserManager().getUser(player).getUserAttributes());
    }

}
