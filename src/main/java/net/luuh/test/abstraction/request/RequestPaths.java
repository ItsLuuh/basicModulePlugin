package net.luuh.test.abstraction.request;

public enum RequestPaths {

    TRANSACTION_SENT(RequestType.TRANSACTION + "-requset-sent"),
    TRANSACTION_RECEIVED(RequestType.TRANSACTION + "-request-received"),
    TRANSACTION_EXPIRED(RequestType.TRANSACTION + "-request-expired"),
    TRANSACTION_ACCEPTED(RequestType.TRANSACTION + "-request-accepted"),
    TRANSACTION_ACCEPTED_SENDER(RequestType.TRANSACTION + "-request-accepted-sender"),
    TRANSACTION_DENIED(RequestType.TRANSACTION + "-request-denied"),
    TRANSACTION_DENIED_SENDER(RequestType.TRANSACTION + "-request-denied-sender"),

    TRADE_SENT(RequestType.TRADE + "-requset-sent"),
    TRADE_RECEIVED(RequestType.TRADE + "-request-received"),
    TRADE_EXPIRED(RequestType.TRADE + "-request-expired"),
    TRADE_ACCEPTED(RequestType.TRADE + "-request-accepted"),
    TRADE_ACCEPTED_SENDER(RequestType.TRADE + "-request-accepted-sender"),
    TRADE_DENIED(RequestType.TRADE + "-request-denied"),
    TRADE_DENIED_SENDER(RequestType.TRADE + "-request-denied-sender")
    ;

    private final String path;
    RequestPaths(String path) {
        this.path = path;

    }

    public String getPath() {
        return path;
    }
}
