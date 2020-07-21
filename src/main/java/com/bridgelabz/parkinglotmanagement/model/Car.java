package com.bridgelabz.parkinglotmanagement.model;

public class Car {
    private final String id;
    private final String regNumber;
    private final double hours;

    public Car(String id, String regNumber, double hours) {
        this.regNumber = regNumber;
        this.id = id;
        this.hours = hours;
    }

    public String getID() {
        return id;
    }

    public double getHours() {
        return hours;
    }
}
