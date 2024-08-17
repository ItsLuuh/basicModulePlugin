package net.luuh.descent.players.manager;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.descent.abstraction.modules.metadata.loader.SQLMetadataLoader;
import net.luuh.descent.abstraction.modules.metadata.loader.SimpleMetadataLoader;
import net.luuh.descent.database.DatabaseProvider;
import net.luuh.descent.players.economy.constant.EconomyType;
import net.luuh.descent.players.economy.object.UserEconomy;
import net.luuh.descent.players.mana.HealthBar;
import net.luuh.descent.players.mana.ManaBar;
import net.luuh.descent.players.objects.UPT;
import net.luuh.descent.players.objects.User;
import net.luuh.descent.players.stats.constant.StatType;
import net.luuh.descent.players.stats.object.UserStats;
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

public class UserManager {

    private final Map<String, User> players = new HashMap<>();

    private final Helper helper;
    private final DatabaseProvider databaseProvider;

    public UserManager(Helper helper) {
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
        return runAsync(() -> {
            String playerName = player.getName();
            try (Connection connection = databaseProvider.getConnection();
                 PreparedStatement select = connection.prepareStatement(SELECT)) {

                select.setString(1, playerName);
                ResultSet selectResult = select.executeQuery();

                UPT upt = null;
                Map<StatType, Double> stats = new HashMap<>();
                Map<EconomyType, BigDecimal> economy = new HashMap<>();

                if (selectResult.next()) {
                    String uptString = selectResult.getString("upt");
                    if (uptString != null) {
                        upt = new UPT(player, uptString);
                    }

                    for (StatType statType : StatType.values()) {
                        stats.put(statType, selectResult.getDouble(statType.getColumn()));
                    }

                    for (EconomyType economyType : EconomyType.values()) {
                        economy.put(economyType, selectResult.getBigDecimal(economyType.getColumn()));
                    }

                } else {
                    try (PreparedStatement register = connection.prepareStatement(REGISTER, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        upt = UPT.generate(player);
                        String uptS = upt.getToken();

                        register.setString(1, uptS);
                        register.setString(2, playerName);
                        register.executeUpdate();
                    }
                }

                if (upt == null) {
                    throw new IllegalStateException("UPT cannot be null");
                }

                if (stats.isEmpty()) {
                    Bukkit.getLogger().warning("Stats map is empty for player: " + playerName);
                }
                if (economy.isEmpty()) {
                    Bukkit.getLogger().warning("Economy map is empty for player: " + playerName);
                }

                UserStats userStats = null;
                try {
                    userStats = new UserStats(this, upt, stats);
                } catch (Exception e) {
                    Bukkit.getLogger().severe("Failed to create UserStats object for player: " + playerName);
                    e.printStackTrace();
                }
                UserEconomy userEconomy = new UserEconomy(this, upt, economy);

                User user = new User(upt, playerName, userEconomy, userStats);

                if (user != null) {
                    for (MetadataLoader<?> loader : helper.getLoaders()) {
                        if (loader instanceof SQLMetadataLoader<?> sqlLoader) {
                            user.loadMetadata(sqlLoader.load(user, connection));
                        } else if (loader instanceof SimpleMetadataLoader<?> simpleLoader) {
                            user.loadMetadata(simpleLoader.load(user));
                        }
                    }
                    players.put(playerName, user);
                    Bukkit.getLogger().info("User loaded and added to manager: " + playerName);
                } else {
                    Bukkit.getLogger().severe("Failed to create User object for player: " + playerName);
                }
            } catch (SQLException e) {
                Bukkit.getLogger().severe("SQL Error while loading user: " + playerName);
                e.printStackTrace();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Unexpected error while loading user: " + playerName);
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

    // USER STATS MANAGER

    public CompletableFuture<Void> setValue(UPT upt, StatType statType, double amount) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement setValue = connection.prepareStatement("UPDATE verion_player SET " + statType.getColumn() + " = ? WHERE upt = ?;")) {
                setValue.setDouble(1, amount);
                setValue.setString(2, upt.getToken());

                setValue.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Double> getValue(UPT upt, StatType statType) {
        return supplyAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement select = connection.prepareStatement(SELECT_BY_UPT)) {
                select.setString(1, upt.getToken());
                ResultSet selectResult = select.executeQuery();

                if (selectResult.next())
                    return selectResult.getBigDecimal(statType.getColumn()).doubleValue();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0d;
        });
    }

    public CompletableFuture<Void> addValue(UPT upt, double amount, StatType statType) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement addValue = connection.prepareStatement("UPDATE verion_player SET " + statType.getColumn() + " = " + statType.getColumn() + " + ? WHERE upt = ?;")) {
                addValue.setDouble(1, amount);
                addValue.setString(2, upt.getToken());

                addValue.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> removeValue(UPT upt, double amount, StatType statType) {
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement removeValue = connection.prepareStatement("UPDATE verion_player SET " + statType.getColumn() + " = " + statType.getColumn() + " - ? WHERE upt = ?;")) {
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
        return runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement delete = connection.prepareStatement(WIPE)) {
                delete.setString(1, player.getName());
                delete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
