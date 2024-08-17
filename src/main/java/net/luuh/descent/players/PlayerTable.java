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
                strength DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                defense DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                healthRegen DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                maxHealth DECIMAL(10, 2) NOT NULL DEFAULT '100.00',
                intelligence DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                manaRegen DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                maxMana DECIMAL(10, 2) NOT NULL DEFAULT '100.00',
                agility DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                playerSpeed DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                critChance DECIMAL(10, 2) NOT NULL DEFAULT '10.00',
                critDamage DECIMAL(10, 2) NOT NULL DEFAULT '1.00',
                firstJoin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            """;
}
