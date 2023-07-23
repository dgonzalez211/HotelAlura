package com.alura.hotel.dto;

import java.io.Serializable;

public abstract class BaseDto implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
