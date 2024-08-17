package net.luuh.descent.modules.remover;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.abstraction.modules.ModuleListener;
import net.luuh.descent.modules.remover.listeners.FoodChangeListener;
import net.luuh.descent.modules.remover.listeners.PhantomSpawnEvent;
import net.luuh.descent.modules.stats.listener.*;

import java.util.Set;

public class Remover extends Module {
    public Remover(Helper helper) {
        super(helper);
    }

    @Override
    protected Set<ModuleListener<?>> getListeners() {
        return Set.of(
                new FoodChangeListener(this.helper, this),
                new PhantomSpawnEvent(this.helper, this)
        );
    }
}
