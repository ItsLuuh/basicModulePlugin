package net.luuh.test.database;

import net.luuh.test.Main;
import net.luuh.test.abstraction.database.DatabaseTable;
import net.luuh.test.players.PlayerTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseProvider {

    public final Main plugin;
    private final HikariProvider connectionProvider;

    public DatabaseProvider(Main plugin) {
        this.plugin = plugin;
        this.connectionProvider = new HikariProvider(plugin);
    }

    @SafeVarargs
    public final <T extends DatabaseTable> void registerTable(T... tables) {
        for (T table : tables)
            try (Connection connection = this.getConnection()) {
                Statement statement = connection.createStatement();

                connection.setAutoCommit(false);

                for (String query : table.getTableQueries())
                    statement.addBatch(query);

                statement.executeBatch();

                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public void assemble() {
        this.connectionProvider.enable();
        registerTable(new PlayerTable(this));
    }

    public void disassemble() {
        this.connectionProvider.disable();
    }

    public Connection getConnection() throws SQLException {
        return connectionProvider.getDataSource().getConnection();
    }
}
