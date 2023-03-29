package com.damb.taskmanagment.domain;

import jakarta.persistence.Entity;


public enum Status {
    OPEN("open"),
    IN_PROGRESS("in progress"),
    CLOSED("closed");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
