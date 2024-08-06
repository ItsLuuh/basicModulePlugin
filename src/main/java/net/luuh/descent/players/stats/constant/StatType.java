package net.luuh.descent.players.stats.constant;

public enum StatType {

    STRENGTH("strength"),
    DEFENSE("defense"),
    HEALTH_REGEN("health_regen"),
    MAX_HEALTH("max_health"),
    INTELLIGENCE("intelligence"),
    MANA_REGEN("mana_regen"),
    MAX_MANA("max_mana"),
    AGILITY("agility"),
    PLAYER_SPEED("player_speed"),
    CRIT_CHANCE("crit_chance"),
    CRIT_DAMAGE("crit_damage"),

    ;

    private final String column;


    StatType(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
