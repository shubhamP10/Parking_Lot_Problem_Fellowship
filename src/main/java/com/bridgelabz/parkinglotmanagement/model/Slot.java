package com.bridgelabz.parkinglotmanagement.model;

public class Slot {

    public int slotId;
    public ParkingLot lot;

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public void setLot(ParkingLot lot) {
        this.lot = lot;
    }

    public ParkingLot getLot() {
        return lot;
    }
}
