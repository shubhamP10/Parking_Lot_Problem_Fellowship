package com.bridgelabz.parkinglotmanagement.model;

import java.sql.Timestamp;

public class Car {
    private final String id;
    private final String regNumber;
    private final double hours;

    private Timestamp parkedTime = null;

    public Car(String id, String regNumber, double hours) {
        this.regNumber = regNumber;
        this.id = id;
        this.hours = hours;
    }

    public Timestamp getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(Timestamp time) {
        this.parkedTime = time;
    }

    public double getHours() {
        return hours;
    }
}
