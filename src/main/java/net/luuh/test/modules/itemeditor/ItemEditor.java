package net.luuh.test.modules.itemeditor;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.Module;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.modules.itemeditor.commands.ItemEditorCommand;

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
