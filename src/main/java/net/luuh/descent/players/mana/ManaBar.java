package net.luuh.descent.players.mana;

import net.luuh.descent.Helper;
import net.luuh.descent.attributes.attributes.Intelligence;
import net.luuh.descent.attributes.attributes.ManaRegen;
import net.luuh.descent.attributes.attributes.MaxMana;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.entity.Player;

public class ManaBar {

    private final User user;
    private Double mana = 0.0;

    public ManaBar(User user) {
        this.user = user;
    }

    public double getManaRegen() {
        if(user.getFinalStat(StatType.MANA_REGEN, ManaRegen.class) == 0) return 0;
        return user.getFinalStat(StatType.MANA_REGEN, ManaRegen.class);
    }

    public double getIntelligence() {
        if(user.getFinalStat(StatType.INTELLIGENCE, Intelligence.class) == 0) return 0;
        return user.getFinalStat(StatType.INTELLIGENCE, Intelligence.class);
    }

    public double getMaxMana() {
        if((user != null ? user.getFinalStat(StatType.MAX_MANA, MaxMana.class) : 0) == 0) return 0;
        return user.getFinalStat(StatType.MAX_MANA, MaxMana.class);
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double amount) {
        this.mana = amount;
    }

    public void addMana(double amount) {
        this.mana += amount;
    }

    public void removeMana(double amount) {
        this.mana -= amount;
    }

}
