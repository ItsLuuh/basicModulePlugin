package net.luuh.test.economy;

import net.luuh.test.abstraction.economy.Economy;
import org.bukkit.entity.Player;

public class DefaultMoney extends Economy {

    public DefaultMoney() {
        super(, );
    }

    @Override
    public void deposit(Player player, double amount) {

    }

    @Override
    public void withdraw(Player player, double amount) {

    }

    @Override
    public double getBalance(Player player) {
        return 0;
    }

    @Override
    public void setBalance(Player player, double amount) {

    }

    @Override
    public boolean hasEnough(Player player, double amount) {
        return false;
    }

    @Override
    public void reset(Player player) {

    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void createAccount() {

    }

    @Override
    public void deleteAccount() {

    }
}
