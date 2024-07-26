package net.luuh.test.players.manager;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.metadata.loader.MetadataLoader;
import net.luuh.test.abstraction.modules.metadata.loader.SQLMetadataLoader;
import net.luuh.test.abstraction.modules.metadata.loader.SimpleMetadataLoader;
import net.luuh.test.database.DatabaseProvider;
import net.luuh.test.players.objects.UPT;
import net.luuh.test.players.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PlayerManager {

    private final Map<String, User> players = new HashMap<>();

    private final Helper helper;
    private final DatabaseProvider databaseProvider;

    public PlayerManager(Helper helper) {
        this.helper = helper;
        this.databaseProvider = helper.getDatabaseProvider();
    }

    private final String SELECT = "SELECT * FROM verion_player WHERE playerName = ?;";
    private final String REGISTER = "INSERT INTO verion_player(uuid, playerName) VALUES (?, ?);";
    private final String UPDATE = "UPDATE verion_player SET balance = ?, credits = ? WHERE playerName = ?;";
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
            UUID uuid = player.getUniqueId();
            String playerName = player.getName();
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement select = connection.prepareStatement(SELECT)) {
                select.setString(1, player.getName());
                ResultSet selectResult = select.executeQuery();

                String uptS = "";
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
                        uptS = upt.toString();

                        register.setString(1, player.getUniqueId().toString());
                        register.setString(2, player.getName());
                        register.setString(3, uptS);
                        register.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                User user = new User(upt, uuid, playerName, balance, credits);

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

    public CompletableFuture<Void> update(Player player) {
        User user = getUser(player);
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = databaseProvider.getConnection(); PreparedStatement update = connection.prepareStatement(UPDATE)) {
                update.setBigDecimal(1, user.getBalance());
                update.setBigDecimal(2, user.getCredits());
                update.setString(3, player.getName());

                update.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> loadOnline() {
        return CompletableFuture.allOf(Bukkit.getOnlinePlayers()
                .stream()
                .map(this::load)
                .toList()
                .toArray(new CompletableFuture[]{}));
    }

    public CompletableFuture<Void> updateOnline() {
        return CompletableFuture.allOf(Bukkit.getOnlinePlayers()
                .stream()
                .map(this::update)
                .toList()
                .toArray(new CompletableFuture[]{}));
    }

    public CompletableFuture<Void> death(Player player, Map<String, Object> parameters) {
        return CompletableFuture.runAsync(() -> {
            StringBuilder setClauseBuilder = new StringBuilder();

            for (String key : parameters.keySet()) {
                setClauseBuilder.append(key).append(" = ?, ");
            }

            if (setClauseBuilder.length() == 0) return;

            setClauseBuilder.setLength(setClauseBuilder.length() - 2);
            String setClause = setClauseBuilder.toString();

            try (Connection connection = databaseProvider.getConnection(); PreparedStatement update = connection.prepareStatement(String.format(WIPE_TEMPLATE, setClause))) {
                int index = 1;
                for (Object value : parameters.values()) {
                    if (value instanceof BigDecimal) {
                        update.setBigDecimal(index++, (BigDecimal) value);
                    } else if (value instanceof Integer) {
                        update.setInt(index++, (int) value);
                    } else if (value instanceof String) {
                        update.setString(index++, (String) value);
                    }
                }
                update.setString(index, player.getName());
                update.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
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

    public Map<String, Object> createParameters(Object... keyValuePairs) {

        /*

        # USAGE #
        #
        Map<String, Object> parameters = helper.getPlayerManager().createParameters(
                "balance", BigDecimal.ZERO,
                "coins", 0,
                "money", 0,
                "candies", 0,
                "name", "John"
        );
        #
        # USAGE #

        */

        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("number of arguments must be even");
        }

        Map<String, Object> parameters = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            if (!(keyValuePairs[i] instanceof String)) {
                throw new IllegalArgumentException("key must be a string");
            }
            parameters.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return parameters;
    }

}
