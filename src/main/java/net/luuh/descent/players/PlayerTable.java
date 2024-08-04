package net.luuh.descent.players;

import net.luuh.descent.abstraction.database.DatabaseTable;
import net.luuh.descent.database.DatabaseProvider;

public class PlayerTable extends DatabaseTable {
    public PlayerTable(DatabaseProvider databaseProvider) {
        super(databaseProvider, PLAYER_TABLE);
    }

    private final static String PLAYER_TABLE = """
            CREATE TABLE IF NOT EXISTS verion_player(
                upt VARCHAR(250) NOT NULL PRIMARY KEY,
                playerName VARCHAR(250) NOT NULL,
                balance DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
                credits DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
                firstJoin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            """;
}
