package com.bridgelabz.parkinglotmanagement.utility;

public class ParkingLotUtility {
    public int parkingLotCapacity;
    public static int lotNumber = 0;
    public static int numberOfParkingLots = 0;

    public ParkingLotUtility(int parkingLotCapacity, int numberOfParkingLots) {
        this.numberOfParkingLots = numberOfParkingLots;
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
}
