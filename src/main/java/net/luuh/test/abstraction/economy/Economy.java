package net.luuh.test.abstraction.economy;

import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Map;

public abstract class Economy {

    protected final String name;
    protected final Map<Player, BigDecimal> accounts;

    protected Economy(String name, Map<Player, BigDecimal> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    protected String getName() {
        return this.name;
    }

    protected Map<Player, BigDecimal> getAccounts() {
        return this.accounts;
    }

    protected BigDecimal getAccount(Player player) {
        return this.accounts.get(player);
    }

    public abstract void deposit(Player player, double amount);

    public abstract void withdraw(Player player, double amount);

    public abstract double getBalance(Player player);

    public abstract void setBalance(Player player, double amount);

    public abstract boolean hasEnough(Player player, double amount);

    public abstract void reset(Player player);

    public abstract void save();

    public abstract void load();

    public abstract void delete();

    public abstract void createAccount();

    public abstract void deleteAccount();

}
