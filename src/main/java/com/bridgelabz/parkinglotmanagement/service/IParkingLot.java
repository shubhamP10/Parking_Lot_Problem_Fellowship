package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;

public interface IParkingLot {

    void parkVehicle(Car car) throws ParkingLotException;

    void unParkVehicle(String key) throws ParkingLotException;

    boolean isParked(Car car);

    void notifyToObserver(String message);

    void addObserver(IParkingObserver monitor);

    String getVehicle(Car car);
}
