package com.bridgelabz.parkinglotmanagement.observer;

public interface IParkingObserver {

    void sendParkingStatus(int totalCarsParked, int parkingLotCapacity);
}
