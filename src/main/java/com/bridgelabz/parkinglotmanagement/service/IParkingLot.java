package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;

public interface IParkingLot {

    void parkVehicle(Car car) throws ParkingLotException;

    void unParkVehicle(Car car) throws ParkingLotException;

    boolean isParked(Car car);

    boolean isUnParked(Car car);
}
