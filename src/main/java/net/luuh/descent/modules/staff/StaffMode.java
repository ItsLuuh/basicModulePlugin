package net.luuh.descent.modules.staff;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.modules.staff.commands.StaffModeCommand;
import net.luuh.descent.modules.staff.manager.StaffModeManager;

import java.util.Set;

public class StaffMode extends Module {
    public StaffMode(Helper helper) {
        super(helper);
    }

    private final StaffModeManager manager = new StaffModeManager();

    @Override
    protected Set<ModuleCommand<?>> getCommands() {
        return Set.of(new StaffModeCommand(this.helper, this));
    }

    public StaffModeManager getSMManager() {
        return manager;
    }
}
