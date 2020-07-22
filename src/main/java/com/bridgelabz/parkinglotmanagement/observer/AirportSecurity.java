package com.bridgelabz.parkinglotmanagement.observer;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;

public class AirportSecurity implements IParkingObserver {
    public String message;


    @Override
    public void sendParkingStatus(int totalCarsParked, int parkingLotCapacity) {
        if (totalCarsParked == parkingLotCapacity)
            message = Notifications.PARKING_LOT_IS_FULL.message;
        else
            message = Notifications.HAVE_SPACE_TO_PARK.message;
    }
}
