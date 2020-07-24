package com.bridgelabz.parkinglotmanagement.model;

import java.sql.Timestamp;

public class Slot {

    public ParkingLot lot;
    private Timestamp parkedTime;
    private int slotId;
    Car car;
    public Slot(Timestamp parkedTime) {
        this.parkedTime = parkedTime;
    }

    public Slot() {
    }

    public Timestamp getParkedTime() {
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

    public int getLotId() {
        return lot.getLotId();
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}
