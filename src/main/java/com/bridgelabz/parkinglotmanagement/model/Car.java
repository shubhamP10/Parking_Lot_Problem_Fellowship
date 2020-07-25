package com.bridgelabz.parkinglotmanagement.model;

import com.bridgelabz.parkinglotmanagement.enums.CarColor;
import com.bridgelabz.parkinglotmanagement.enums.CarCompany;
import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.enums.DriverType;

public class Car {
    private final String plateNumber;
    private final DriverType driverType;
    private final CarType carType;
    private final CarColor carColor;
    private final CarCompany carCompany;

    public Car(String plateNumber, DriverType driverType, CarType carType, CarColor carColor, CarCompany carCompany) {
        this.plateNumber = plateNumber;
        this.driverType = driverType;
        this.carType = carType;
        this.carColor = carColor;
        this.carCompany = carCompany;
    }

    public String getPlateNumber() {
        return plateNumber;
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

    public CarCompany getCarCompany() {
        return carCompany;
    }
}
