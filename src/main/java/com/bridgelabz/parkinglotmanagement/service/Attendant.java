package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.model.Car;

import java.util.Map;

public class Attendant {

    String key;
    int lotNumber = 1;

    public String parkVehicle(Map<String, Car> parkingMap) {
        if (parkingMap.size() == 0)
            this.key = String.valueOf(lotNumber);
        for (String key : parkingMap.keySet()) {
            this.key = key;
            if (!parkingMap.get(key).equals(key))
                this.key = String.valueOf(lotNumber);
        }
        lotNumber++;
        return key;
    }
}
