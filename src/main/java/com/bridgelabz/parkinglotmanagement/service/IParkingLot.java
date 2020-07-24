package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.model.Slot;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;

import java.util.Map;

public interface IParkingLot {

    Map<Slot, Car> park(Car car) throws ParkingLotException;

    Map<Slot, Car> unPark(Car car) throws ParkingLotException;

    void notifyToObserver(int totalCarsParked);

    void addObserver(IParkingObserver... observers);

}
