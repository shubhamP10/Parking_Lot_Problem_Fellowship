package com.bridgelabz.parkinglotmanagement.utility;

import java.sql.Timestamp;
import java.util.Date;

public class ParkingLotUtility {
    public static int lotNumber = 0;
    public static int numberOfParkingLots = 0;
    public int parkingLotCapacity;

    public ParkingLotUtility(int parkingLotCapacity, int numberOfParkingLots) {
        ParkingLotUtility.numberOfParkingLots = numberOfParkingLots;
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public static int assignLot(int slotId) {
        switch (numberOfParkingLots) {
            case 2:
                switch (slotId % numberOfParkingLots) {
                    case 0:
                        lotNumber = 2;
                        break;
                    case 1:
                        lotNumber = 1;
                        break;
                }
                break;
            case 4:
                switch (slotId % numberOfParkingLots) {
                    case 0:
                        lotNumber = 4;
                        break;
                    case 1:
                        lotNumber = 1;
                        break;
                    case 2:
                        lotNumber = 2;
                        break;
                    case 3:
                        lotNumber = 3;
                        break;
                }
                break;
        }

        return lotNumber;
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

    public int getNumberOfSlotsPerLot() {
        return parkingLotCapacity / numberOfParkingLots;
    }

}
