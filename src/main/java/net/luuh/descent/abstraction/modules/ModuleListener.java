package net.luuh.descent.abstraction.modules;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.DefaultListener;

public abstract class ModuleListener<T extends Module> extends DefaultListener {

    protected final T module;

    public ModuleListener(Helper helper, T module) {
        super(helper);
        this.module = module;
    }

}
