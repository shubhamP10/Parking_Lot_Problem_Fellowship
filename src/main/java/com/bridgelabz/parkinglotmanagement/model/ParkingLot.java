package com.bridgelabz.parkinglotmanagement.model;

public class ParkingLot {

    private final int lotId;
    int carCounter = 0;
    public ParkingLot(int lotId) {
        this.lotId = lotId;
        carCounter++;
    }

    public int getLotId() {
        return lotId;
    }

    public int getCarCounter() {
        return carCounter;
    }
}
