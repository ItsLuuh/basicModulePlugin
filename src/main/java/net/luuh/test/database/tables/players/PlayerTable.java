package net.luuh.test.database.tables.players;

import net.luuh.test.abstraction.database.DatabaseTable;
import net.luuh.test.database.DatabaseProvider;

public class PlayerTable extends DatabaseTable {
    public PlayerTable(DatabaseProvider databaseProvider) {
        super(databaseProvider, PLAYER_TABLE);
    }

    private final static String PLAYER_TABLE = """
            CREATE TABLE IF NOT EXISTS descent_player(
                id INT AUTO_INCREMENT PRIMARY KEY,
                uuid TEXT NOT NULL,
                playerName TEXT NOT NULL,
                balance DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
                credits DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
                firstJoin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()
            );
            """;
}
