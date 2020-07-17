package com.bridgelabz.parkinglotmanagement.model;

public class Car {
    private String id;
    private String regNumber;

    public Car(String id, String regNumber) {
        this.regNumber = regNumber;
        this.id = id;
    }

    public String getID() {
        return id;
    }
}
