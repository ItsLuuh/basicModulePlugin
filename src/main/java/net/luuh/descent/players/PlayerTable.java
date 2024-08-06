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
                strength INT NOT NULL DEFAULT 1,
                defense INT NOT NULL DEFAULT 1,
                healthRegen INT NOT NULL DEFAULT 1,
                maxHealth INT NOT NULL DEFAULT 100,
                intelligence INT NOT NULL DEFAULT 1,
                manaRegen INT NOT NULL DEFAULT 1,
                maxMana INT NOT NULL DEFAULT 100,
                agility INT NOT NULL DEFAULT 1,
                playerSpeed INT NOT NULL DEFAULT 1,
                critChance INT NOT NULL DEFAULT 10,
                critDamage INT NOT NULL DEFAULT 1,
                firstJoin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            """;
}
