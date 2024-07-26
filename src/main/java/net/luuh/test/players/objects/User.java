package net.luuh.test.players.objects;

import net.luuh.test.abstraction.modules.metadata.Metadata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class User {

    private final UPT upt;
    private final UUID uuid;
    private final String displayName;
    private BigDecimal balance; // Polus balance
    private BigDecimal credits;
    private final Map<Class<? extends Metadata>, Metadata> metadata;

    public User(UPT upt, UUID uuid, String displayName, BigDecimal balance, BigDecimal credits) {
        this.upt = upt;
        this.uuid = uuid;
        this.displayName = displayName;
        this.balance = balance;
        this.credits = credits;
        this.metadata = new HashMap<>();
    }

    public UPT getUpt() {
        return upt;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Player getPlayer() {return Bukkit.getPlayer(displayName);}

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setBalance(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    public void setCredits(double credits) {
        this.credits = BigDecimal.valueOf(credits);
    }

    public void addBalance(double balance) {
        this.balance = this.balance.add(BigDecimal.valueOf(balance));
    }

    public void addCredits(double credits) {
        this.credits = this.credits.add(BigDecimal.valueOf(credits));
    }

    public void removeBalance(double balance) {
        this.balance = this.balance.subtract(BigDecimal.valueOf(balance));
    }

    public void removeCredits(double credits) {
        this.credits = this.credits.subtract(BigDecimal.valueOf(credits));
    }

    public boolean hasBalance(double amount){return balance.doubleValue() >= amount;}

    public boolean hasCredits(double amount){return credits.doubleValue() >= amount;}

    public <T extends Metadata> Optional<T> getMetadata(Class<T> dataClass) {
        return Optional.ofNullable(dataClass.cast(metadata.get(dataClass)));
    }

    public void loadMetadata(Metadata data) {
        if (data != null) metadata.put(data.getClass(), data);
    }

    public <T extends  Metadata> void unloadMetadata(Class<T> clazz) {
        metadata.remove(clazz);
    }




}
