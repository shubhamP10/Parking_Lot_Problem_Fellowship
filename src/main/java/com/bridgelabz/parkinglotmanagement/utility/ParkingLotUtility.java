package com.bridgelabz.parkinglotmanagement.utility;

import java.sql.Timestamp;
import java.util.Date;

public class ParkingLotUtility {
    public int parkingLotCapacity;
    public static int lotNumber = 0;
    public static int numberOfParkingLots = 0;

    public ParkingLotUtility(int parkingLotCapacity, int numberOfParkingLots) {
        ParkingLotUtility.numberOfParkingLots = numberOfParkingLots;
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public static int assignLot(int slotId) {
        if (slotId % numberOfParkingLots == 0) {
            lotNumber = 4;
        } else if (slotId % numberOfParkingLots == 1) {
            lotNumber = 1;
        } else if (slotId % numberOfParkingLots == 2) {
            lotNumber = 2;
        } else if (slotId % numberOfParkingLots == 3) {
            lotNumber = 3;
        }
        return lotNumber;
    }

    public int getNumberOfSlotsPerLot() {
        return parkingLotCapacity / numberOfParkingLots;
    }

    //Method To Get Current Timestamp
    public static Timestamp getCurrentTime() {
        //Date object
        Date date = new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        return new Timestamp(time);
    }

}
