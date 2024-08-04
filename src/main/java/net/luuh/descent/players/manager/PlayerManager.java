package net.luuh.descent.players.manager;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.descent.abstraction.modules.metadata.loader.SQLMetadataLoader;
import net.luuh.descent.abstraction.modules.metadata.loader.SimpleMetadataLoader;
import net.luuh.descent.database.DatabaseProvider;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.economy.object.UserEconomy;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class PlayerManager {

    private final Map<String, User> players = new HashMap<>();

    private final Helper helper;
    private final DatabaseProvider databaseProvider;

    public PlayerManager(Helper helper) {
        this.helper = helper;
        this.databaseProvider = helper.getDatabaseProvider();
    }

    private final String SELECT = "SELECT * FROM verion_player WHERE playerName = ?;";
    private final String SELECT_BY_UPT = "SELECT * FROM verion_player WHERE upt = ?;";
    private final String REGISTER = "INSERT INTO verion_player(upt, playerName) VALUES (?, ?);";
    private final String WIPE = "DELETE FROM verion_player WHERE playerName = ?";
    private final String WIPE_TEMPLATE = "UPDATE verion_player SET %s WHERE playerName = ?;";

    public void forEach(Consumer<User> consumer) {
        players.values().forEach(consumer);
    }

    public User getUser(Player player) {
        return players.get(player.getName());
    }

    public User getUser(String playerName) {
        return players.get(playerName);
    }

    public void editUser(String playerName, Consumer<User> consumer) {
        User user = getUser(playerName);

        if (user == null) return;

        consumer.accept(user);
    }

    public void remove(Player player) {
        remove(player.getName());
    }

    public void remove(String playerName) {
        players.remove(playerName);
    }

    public CompletableFuture<Void> load(Player player) {
        return CompletableFuture.runAsync(() -> {
            String playerName = player.getName();
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement select = connection.prepareStatement(SELECT)) {
                select.setString(1, player.getName());
                ResultSet selectResult = select.executeQuery();

                String uptS;
                UPT upt = new UPT(player, "0");
                BigDecimal balance = new BigDecimal(0);
                BigDecimal credits = new BigDecimal(0);
                if (selectResult.next()) {
                    upt = new UPT (player, selectResult.getString("upt"));
                    balance = selectResult.getBigDecimal("balance");
                    credits = selectResult.getBigDecimal("credits");

                } else {
                    try (PreparedStatement register = connection.prepareStatement(REGISTER, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        upt = UPT.generate(player);
                        uptS = upt.getToken();

                        register.setString(1, uptS);
                        register.setString(2, player.getName());
                        register.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                UserEconomy userEconomy = new UserEconomy(this, upt, new HashMap<>(Map.of(EconomyType.BALANCE, balance, EconomyType.CREDITS, credits)));

                User user = new User(upt, playerName, userEconomy);

                for (MetadataLoader<?> loader : helper.getLoaders()) {
                    if (loader instanceof SQLMetadataLoader<?> sqlLoader)
                        user.loadMetadata(sqlLoader.load(user, connection));

                    if (loader instanceof SimpleMetadataLoader<?> simpleLoader)
                        user.loadMetadata(simpleLoader.load(user));
                }

                players.put(player.getName(), user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // USER ECONOMY MANAGER

    public CompletableFuture<Void> setValue(UPT upt, EconomyType economyType, double amount) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement setValue = connection.prepareStatement("UPDATE verion_player SET " + economyType.getColumn() + " = ? WHERE upt = ?;")) {
                setValue.setDouble(1, amount);
                setValue.setString(2, upt.getToken());

                setValue.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Double> getValue(UPT upt, EconomyType economyType) {
        return supplyAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement select = connection.prepareStatement(SELECT_BY_UPT)) {
                select.setString(1, upt.getToken());
                ResultSet selectResult = select.executeQuery();

                if (selectResult.next())
                    return selectResult.getBigDecimal(economyType.getColumn()).doubleValue();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0d;
        });
    }

    public CompletableFuture<Void> addValue(UPT upt, double amount, EconomyType economyType) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement addValue = connection.prepareStatement("UPDATE verion_player SET " + economyType.getColumn() + " = " + economyType.getColumn() + " + ? WHERE upt = ?;")) {
                addValue.setDouble(1, amount);
                addValue.setString(2, upt.getToken());

                addValue.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> removeValue(UPT upt, double amount, EconomyType economyType) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement removeValue = connection.prepareStatement("UPDATE verion_player SET " + economyType.getColumn() + " = " + economyType.getColumn() + " - ? WHERE upt = ?;")) {
                removeValue.setDouble(1, amount);
                removeValue.setString(2, upt.getToken());

                removeValue.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // PLAYER MANAGER

    public CompletableFuture<Void> loadOnline() {
        return CompletableFuture.allOf(Bukkit.getOnlinePlayers()
                .stream()
                .map(this::load)
                .toList()
                .toArray(new CompletableFuture[]{}));
    }

    public CompletableFuture<Void> wipePlayer(Player player) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement delete = connection.prepareStatement(WIPE)) {
                delete.setString(1, player.getName());
                delete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
