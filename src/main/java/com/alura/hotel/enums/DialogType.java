package com.alura.hotel.enums;

public enum DialogType {

    WARNING("WARNING"),
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    INFO("INFO");


    private final String type;


    DialogType(String type) {
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
