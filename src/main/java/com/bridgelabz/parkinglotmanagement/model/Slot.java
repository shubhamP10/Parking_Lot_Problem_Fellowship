package com.bridgelabz.parkinglotmanagement.model;

import java.sql.Timestamp;

public class Slot {

    private Timestamp parkedTime;
    private int slotId;
    public ParkingLot lot;

    public Slot(Timestamp parkedTime) {
        this.parkedTime = parkedTime;
    }

    public Slot() {
    }

    public Timestamp getParkedTime(){
        return parkedTime;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public void setLot(ParkingLot lot) {
        this.lot = lot;
    }

    public int getLotId(){
        return lot.getLotId();
    }
}
