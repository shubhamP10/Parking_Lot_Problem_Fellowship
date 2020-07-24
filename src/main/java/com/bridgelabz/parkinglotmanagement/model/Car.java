package com.bridgelabz.parkinglotmanagement.model;

import com.bridgelabz.parkinglotmanagement.enums.CarColor;
import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.enums.DriverType;

public class Car {
    private final String regNumber;
    private final DriverType driverType;
    private final CarType carType;
    private final CarColor carColor;

    public Car(String regNumber, DriverType driverType, CarType carType, CarColor carColor) {
        this.regNumber = regNumber;
        this.driverType = driverType;
        this.carType = carType;
        this.carColor = carColor;
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

    public CarColor getCarColor() {
        return carColor;
    }
}
