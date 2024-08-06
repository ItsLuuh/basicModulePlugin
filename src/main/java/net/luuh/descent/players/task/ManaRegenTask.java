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
            double intelligence = 0;
            double manaRegen = 0;
            double maxMana = 0;
            double mana = 0;
            try {
                intelligence = user.getUserStats().get(StatType.INTELLIGENCE).get();
                intelligence += user.getUserStats().getManaBar().getIntelligence();
                manaRegen = user.getUserStats().get(StatType.HEALTH_REGEN).get();
                manaRegen += user.getUserStats().getManaBar().getManaRegen();
                maxMana = user.getUserStats().get(StatType.MAX_HEALTH).get();
                maxMana += user.getUserStats().getManaBar().getMaxMana();
                mana = user.getUserStats().getManaBar().getMana();

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if(manaRegen > maxMana) return;
            if(mana < maxMana) user.getUserStats().getManaBar().addMana((intelligence + manaRegen)*0.02);
        });
    }
}