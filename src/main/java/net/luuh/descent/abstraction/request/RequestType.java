package net.luuh.descent.abstraction.request;

public enum RequestType {

    TRANSACTION("transaction"),
    TRADE("trade"),
    ;


    private final String requestType;
    RequestType(String requestType) {

        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }


}
