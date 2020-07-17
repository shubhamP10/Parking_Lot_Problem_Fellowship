package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;

public interface IParkingLot {

    void parkVehicle(Object vehicle) throws ParkingLotException;

    void unParkVehicle(Object vehicle) throws ParkingLotException;

    boolean isParked();

    boolean isUnParked();
}
