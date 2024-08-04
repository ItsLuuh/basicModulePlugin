package net.luuh.descent.modules.itemeditor;

import net.luuh.descent.abstraction.modules.Module;
import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.ModuleCommand;
import net.luuh.descent.modules.itemeditor.commands.ItemEditorCommand;

import java.util.Set;

public class ItemEditor extends Module {
    public ItemEditor(Helper helper) {
        super(helper);
    }

    @Override
    protected Set<ModuleCommand<?>> getCommands() {
        return Set.of(new ItemEditorCommand(this.helper, this));
    }

}
