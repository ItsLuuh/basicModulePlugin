package net.luuh.test.abstraction.modules;

public class CommandSuccessException extends Exception {
    private final boolean success;

    // Constructor that accepts a boolean and a message
    public CommandSuccessException(boolean success, String message) {
        super(message);
        this.success = success;
    }

    // Constructor that accepts a boolean, a message and a cause
    public CommandSuccessException(boolean success, String message, Throwable cause) {
        super(message, cause);
        this.success = success;
    }

    public CommandSuccessException(boolean success) {
        this.success = success;
    }

    // Constructor that accepts a boolean and a cause
    public boolean isSuccess() {
        return success;
    }
}
