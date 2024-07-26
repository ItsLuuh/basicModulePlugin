package net.luuh.test.modules.essentials;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.Module;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.modules.essentials.commands.CoreCommand;
import net.luuh.test.modules.essentials.commands.FlyCommand;
import net.luuh.test.modules.essentials.commands.gamemodes.*;

import java.util.Set;

public class Essentials extends Module {

    public Essentials(Helper helper) {
        super(helper);
    }

    @Override
    protected Set<ModuleCommand<?>> getCommands() {
        return Set.of(
                new CoreCommand(this.helper, this),
                new GamemodeCommand(this.helper, this),
                new SurvivalCommand(this.helper, this),
                new CreativeCommand(this.helper, this),
                new SpectatorCommand(this.helper, this),
                new AdventureCommand(this.helper, this),
                new FlyCommand(this.helper, this)
        );
    }

}
