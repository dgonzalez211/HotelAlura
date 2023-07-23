module alura_hotel {

    requires MaterialFX;
    requires VirtualizedFX;
    requires jdk.localedata;

    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;

    requires mfx.core;
    requires mfx.effects;
    requires mfx.localization;
    requires mfx.resources;
    requires com.google.gson;
    requires okhttp3;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.google.common;
    requires java.logging;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.alura.hotel to javafx.fxml, spring.core;
    exports com.alura.hotel;
    opens com.alura.hotel.controllers to javafx.fxml, spring.core;
    exports com.alura.hotel.controllers;
    opens com.alura.hotel.util to javafx.fxml;
    exports com.alura.hotel.util;
    opens com.alura.hotel.config to javafx.fxml, spring.core;
    exports com.alura.hotel.config;
    opens com.alura.hotel.services to spring.core, spring.beans;
    exports com.alura.hotel.services;
    exports com.alura.hotel.exceptions;
    exports com.alura.hotel.dto;
    opens com.alura.hotel.dto to com.google.gson;
    opens com.alura.hotel.components to javafx.fxml;
    exports com.alura.hotel.components;
    exports com.alura.hotel.services.impl;
    exports com.alura.hotel.router;
    exports com.alura.hotel.enums;
    opens com.alura.hotel.services.impl to spring.beans, spring.core;
    exports com.alura.hotel.services.adapter;
    opens com.alura.hotel.services.adapter to javafx.fxml;
    exports com.alura.hotel.services.deserializer;
    opens com.alura.hotel.services.deserializer to javafx.fxml;
    exports com.alura.hotel.services.request;
    opens com.alura.hotel.services.request to javafx.fxml, com.google.gson;
    exports com.alura.hotel.api;

}