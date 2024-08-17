package net.luuh.descent.constants;

public enum CommandsCaseExceptions {

    NO_PERMS("case-no-perms"),
    INVALID_ARGS("case-invalid-args"),
    INVALID_PLAYER("case-invalid-player"),
    INVALID_AMOUNT("case-invalid-amount"),
    ;

    private final String permission;

    CommandsCaseExceptions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
