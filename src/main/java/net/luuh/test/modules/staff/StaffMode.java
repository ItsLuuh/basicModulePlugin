package net.luuh.test.modules.staff;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.Module;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.modules.staff.commands.StaffModeCommand;
import net.luuh.test.modules.staff.manager.StaffModeManager;

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
