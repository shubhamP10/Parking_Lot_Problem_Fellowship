package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.utility.IParkingMonitor;

public interface IParkingLot {

    void parkVehicle(Car car) throws ParkingLotException;

    void unParkVehicle(Car car) throws ParkingLotException;

    boolean isParked(Car car);

    void notifyToMonitor(String message);

    void addMonitor(IParkingMonitor monitor);
}
