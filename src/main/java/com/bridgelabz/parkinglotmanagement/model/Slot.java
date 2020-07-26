package com.bridgelabz.parkinglotmanagement.model;

import java.time.LocalDateTime;

public class Slot {

    public ParkingLot lot;
    Car car;
    private LocalDateTime parkedTime;
    private int slotId;

    public Slot(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }

    public Slot() {
    }

    public LocalDateTime getParkedTime() {
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
