package net.luuh.descent.players.mana;

import net.luuh.descent.attributes.AttributeManager;
import net.luuh.descent.attributes.attributes.Intelligence;
import net.luuh.descent.attributes.attributes.ManaRegen;
import net.luuh.descent.attributes.attributes.MaxMana;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class ManaBar {

    private final UserStats userStats;
    private Double mana;
    private final MaxMana maxMana = (MaxMana) AttributeManager.getAttributes().get(MaxMana.class);
    private final ManaRegen manaRegen = (ManaRegen) AttributeManager.getAttributes().get(ManaRegen.class);
    private final Intelligence intelligence = (Intelligence) AttributeManager.getAttributes().get(Intelligence.class);

    public ManaBar(UserStats userStats) {
        this.userStats = userStats;
    }

    public UPT getPlayerUPT() {
        return userStats.getPlayerUPT();
    }

    public Player getPlayer() {
        return userStats.getPlayerUPT().getPlayer();
    }

    public double getManaRegen() {
        return userStats.get(manaRegen) + userStats.getVisual(StatType.MANA_REGEN);
    }

    public double getIntelligence() {
        return userStats.get(intelligence) + userStats.getVisual(StatType.INTELLIGENCE);
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

    public double getMaxMana() {
        return userStats.get(maxMana);
    }

    public void setMaxMana(double amount) {
        userStats.set(amount, maxMana);
    }

    public void addMaxMana(double amount) {
        userStats.add(amount, maxMana);
    }

    public void removeMaxMana(double amount) {
        userStats.remove(amount, maxMana);
    }

}
