package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.model.ParkingLot;
import com.bridgelabz.parkinglotmanagement.model.Slot;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;
import com.bridgelabz.parkinglotmanagement.utility.ParkingLotUtility;

import java.util.*;


public class Attendant implements IParkingLot{
    private final List<IParkingObserver> observersList;
    private final Map<Slot, Car> parkingMap;
    public int parkingLotCapacity;
    public int numberOfParkingLots;
    public int numberOfSlotsPerLot;
    public int slotCounter = 0;


    public Attendant(int parkingLotCapacity, int numberOfParkingLots, int numberOfSlotsPerLot) {
        this.parkingLotCapacity = parkingLotCapacity;
        this.numberOfParkingLots = numberOfParkingLots;
        this.numberOfSlotsPerLot = numberOfSlotsPerLot;
        this.parkingMap = new HashMap<>();
        observersList = new ArrayList<>();
    }

    public void addObserver(IParkingObserver... observers) {
        Collections.addAll(observersList, observers);
    }

    public Map<Slot, Car> park(Car car) {
        if (parkingMap.size() > this.parkingLotCapacity)
            throw new ParkingLotException(ParkingLotException.ExceptionType.LOT_FULL);
        Slot slot = new Slot();
        slotCounter += 1;
        slot.setSlotId(slotCounter);
        ParkingLot parkingLot = new ParkingLot(ParkingLotUtility.assignLot(slot.getSlotId()));
        slot.setLot(parkingLot);
        parkingMap.put(slot, car);
        this.notifyToObserver(parkingMap.size());
        return parkingMap;
    }

    public Map<Slot, Car> unPark(Car car) throws ParkingLotException {
        if (!parkingMap.containsValue(car))
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        Set<Slot> slots = parkingMap.keySet();
        for (Slot slot : slots) {
            if (parkingMap.get(slot).equals(car)) {
                parkingMap.remove(slot);
                this.notifyToObserver(parkingMap.size());
            }
        }
        return parkingMap;
    }

    @Override
    public void notifyToObserver(int totalCarsParked) {
        for (IParkingObserver observer: observersList){
            observer.sendParkingStatus(totalCarsParked, this.parkingLotCapacity);
        }
    }


}
