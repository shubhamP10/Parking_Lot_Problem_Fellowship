package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.Owner;

import java.util.Map;

public class Attendant {
    Owner owner = new Owner();

    public String parkVehicle(Map<String, Car> parkingMap) {
        return owner.generateKeyForLot(parkingMap);
    }

    public void unParkVehicle(String key) {
        owner.updateUnParkedVehicle(key);
    }
}
