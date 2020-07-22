package com.bridgelabz.parkinglotmanagement.model;

import java.sql.Timestamp;

public class Car {
    private final String regNumber;

    private Timestamp parkedTime = null;

    public Car(String regNumber) {
        this.regNumber = regNumber;
    }

    public Timestamp getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(Timestamp time) {
        this.parkedTime = time;
    }
}
