package net.luuh.descent.players.task;

import net.luuh.descent.Helper;
import net.luuh.descent.players.mana.ManaBar;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegenTask extends BukkitRunnable {

    private final Helper helper;

    public ManaRegenTask(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        helper.getUserManager().forEach(user -> {
            if (user == null) {
                throw new IllegalStateException("User is not initialized");
            }
            ManaBar manaBar = user.getManaBar();
            if (manaBar == null) {
                throw new IllegalStateException("HealthBar is not initialized");
            }
            double intelligence= user.getManaBar().getIntelligence();
            double manaRegen= user.getManaBar().getManaRegen();
            double maxMana= user.getManaBar().getMaxMana();
            double mana= user.getManaBar().getMana();

            if(mana == maxMana) return;
            if(manaRegen + mana >= maxMana) user.getManaBar().setMana(maxMana);
            if(mana < maxMana) user.getManaBar().addMana((intelligence + manaRegen)*0.02);
        });
    }
}