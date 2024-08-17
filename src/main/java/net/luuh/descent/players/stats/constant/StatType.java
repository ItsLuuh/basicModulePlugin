package net.luuh.descent.players.stats.constant;

public enum StatType {

    STRENGTH("strength"),
    DEFENSE("defense"),
    HEALTH_REGEN("healthRegen"),
    MAX_HEALTH("maxHealth"),
    INTELLIGENCE("intelligence"),
    MANA_REGEN("manaRegen"),
    MAX_MANA("maxMana"),
    AGILITY("agility"),
    PLAYER_SPEED("playerSpeed"),
    CRIT_CHANCE("critChance"),
    CRIT_DAMAGE("critDamage"),

    ;

    private final String column;


    StatType(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
