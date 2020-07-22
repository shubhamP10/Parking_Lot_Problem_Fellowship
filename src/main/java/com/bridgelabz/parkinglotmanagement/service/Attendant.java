package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.model.Car;

import java.util.Map;

public class Attendant {
    String lotNumber;
    int index = 1;

    public String parkVehicle(Map<String, Car> parkingMap) {
        return this.generateKeyForLot(parkingMap);
    }

    private String generateKeyForLot(Map<String, Car> parkingMap) {
        if (parkingMap.size() == 0)
            this.lotNumber = String.valueOf(index);
        parkingMap.keySet().forEach(lotNumber -> {
            this.lotNumber = lotNumber;
            if (!parkingMap.get(lotNumber).equals(lotNumber))
                this.lotNumber = String.valueOf(index);
        });
        index++;
        return lotNumber;
    }
}
