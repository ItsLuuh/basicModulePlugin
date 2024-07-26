package net.luuh.test.abstraction.modules;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.DefaultListener;

public abstract class ModuleListener<T extends Module> extends DefaultListener {

    protected final T module;

    public ModuleListener(Helper helper, T module) {
        super(helper);
        this.module = module;
    }

}
