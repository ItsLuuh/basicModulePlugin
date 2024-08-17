package net.luuh.descent.modules.stats;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.modules.essentials.commands.CoreCommand;
import net.luuh.descent.modules.essentials.commands.FlyCommand;
import net.luuh.descent.modules.essentials.commands.gamemodes.*;
import net.luuh.descent.modules.stats.listener.*;

import java.util.Set;

public class Stats extends Module {
    public Stats(Helper helper) {
        super(helper);
    }

    @Override
    protected Set<ModuleListener<?>> getListeners() {
        return Set.of(
                new ArmorChangeListener(this.helper, this),
                new ArmorSwapListener(this.helper, this),
                new HotbarSwapListener(this.helper, this),
                new PlayerDamagedEvent(this.helper, this),
                new PlayerRegainHealthEvent(this.helper, this)
        );
    }


}
