package net.luuh.descent.players.task;

import net.luuh.descent.Helper;
import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.*;
import net.luuh.descent.players.stats.constant.StatType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;

public class ManaRegenTask extends BukkitRunnable {

    private final Helper helper;

    public ManaRegenTask(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        helper.getPlayerManager().forEach(user -> {
            double intelligence= user.getUserStats().getManaBar().getIntelligence();
            double manaRegen= user.getUserStats().getManaBar().getManaRegen();
            double maxMana= user.getUserStats().getManaBar().getMaxMana();
            double mana= user.getUserStats().getManaBar().getMana();

            if(manaRegen > maxMana) return;
            if(mana < maxMana) user.getUserStats().getManaBar().addMana((intelligence + manaRegen)*0.02);
        });
    }
}