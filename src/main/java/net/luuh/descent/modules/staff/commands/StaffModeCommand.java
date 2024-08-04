package net.luuh.descent.modules.staff.commands;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.modules.staff.StaffMode;
import org.bukkit.entity.Player;

public class StaffModeCommand extends ModuleCommand<StaffMode> {
    public StaffModeCommand(Helper helper, StaffMode module) {
        super(helper, module, "staffmode", "staff");
    }

    @Override
    protected void execute(Player player, String[] args) {

        boolean staffModeBool = module.getSMManager().isStaffMode(player);
        if(staffModeBool){
            module.getSMManager().setStaffMode(player, false);
            player.getInventory().clear();
            module.getSMManager().applyInventory(player);
            player.sendMessage(
                    helper.getRMUtils().readTranslation(player,"staffmode-quit")
            );
        } else {
            module.getSMManager().setStaffMode(player, true);
            module.getSMManager().setInventory(player, player.getInventory());
            player.getInventory().clear();
            player.sendMessage(
                    helper.getRMUtils().readTranslation(player,"staffmode-join")
            );
        }

    }
}
