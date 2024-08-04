package net.luuh.descent.players.economy.constant;

public enum EconomyType {

    BALANCE("balance"),
    CREDITS("credits");

    private final String column;

    EconomyType(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
