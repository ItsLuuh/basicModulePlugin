package net.luuh.descent.modules.essentials.commands.time;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.CommandSuccessException;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.constants.CommandsCaseExceptions;
import net.luuh.descent.constants.Permission;
import net.luuh.descent.modules.essentials.Essentials;
import net.luuh.descent.utils.RMUtils;
import org.bukkit.entity.Player;

public class DayCommand extends ModuleCommand<Essentials> {
    public DayCommand(Helper helper, Essentials module) {
        super(helper, module, "day");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        if(!Permission.TIMECOMMANDS.has(player)) throw new CommandSuccessException(false, helper.getRMUtils(), CommandsCaseExceptions.NO_PERMS, player);
        player.getWorld().setTime(1000);
        player.sendMessage(helper.getRMUtils().readTranslation(player, "day"));
        throw new CommandSuccessException(true);
    }
}
