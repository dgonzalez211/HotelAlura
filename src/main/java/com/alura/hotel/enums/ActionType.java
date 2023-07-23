package com.alura.hotel.enums;

public enum ActionType {

    CONFIRM("CONFIRM"),
    CANCEL("CANCEL"),
    DELETE("DELETE"),
    DONE("DONE"),
    WARNING("WARNING"),
    INFO("INFO"),
    ERROR("ERROR"),
    SUCCESS("SUCCESS");


    private final String type;


    ActionType(String type) {
        this.type = type;
    }

    private String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type;
    }
}
