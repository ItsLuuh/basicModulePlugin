package net.luuh.descent.constants;

import org.bukkit.entity.Player;

public enum Permission {

    PREFIX("verion"),
    RELOAD(PREFIX + ".reload.all"),
    RELOAD_CONFIG(PREFIX + ".reload.config"),
    RELOAD_MEX(PREFIX + ".reload.mex"),
    GAMEMODE(PREFIX + ".gamemode"),
    GAMEMODE_OTHERS(PREFIX + ".gamemode.others"),
    GAMEMODE_SURVIVAL(PREFIX + ".gamemode.survival"),
    GAMEMODE_CREATIVE(PREFIX + ".gamemode.creative"),
    GAMEMODE_ADVENTURE(PREFIX + ".gamemode.adventure"),
    GAMEMODE_SPECTATOR(PREFIX + ".gamemode.spectator"),
    FLY(PREFIX + ".fly"),
    FLY_OTHERS(PREFIX + ".fly.others"),
    ITEM_EDITOR(PREFIX + ".itemeditor"),
    ITEM_EDITOR_RENAME(PREFIX + ".itemeditor.rename"),
    ITEM_EDITOR_LORE(PREFIX + ".itemeditor.lore"),
    ITEM_EDITOR_ENCHANT(PREFIX + ".itemeditor.enchant"),
    STAFFMODE_TOGGLE(PREFIX + ".staffmode.toggle"),
    STAFFMODE_TOGGLE_OTHERS(PREFIX + ".staffmode.toggle.others"),

    ;

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public boolean has(Player player) {
        return player.hasPermission(this.permission);
    }
}
