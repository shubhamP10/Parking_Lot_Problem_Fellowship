package com.bridgelabz.parkinglotmanagement.model;

import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.enums.DriverType;

public class Car {
    private final String regNumber;
    private final DriverType driverType;
    private final CarType carType;

    public Car(String regNumber, DriverType driverType, CarType carType) {
        this.regNumber = regNumber;
        this.driverType = driverType;
        this.carType = carType;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public CarType getCarType() {
        return carType;
    }
}
