package com.bridgelabz.parkinglotmanagement.model;

public class Car {
    private final String id;
    private final String regNumber;

    public Car(String id, String regNumber) {
        this.regNumber = regNumber;
        this.id = id;
    }

    public String getID() {
        return id;
    }
}
