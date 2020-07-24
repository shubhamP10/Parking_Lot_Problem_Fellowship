package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.model.ParkingLot;
import com.bridgelabz.parkinglotmanagement.model.Slot;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;
import com.bridgelabz.parkinglotmanagement.utility.ParkingLotUtility;

import java.util.*;


public class Attendant implements IParkingLot {
    private final List<IParkingObserver> observersList;
    private final Map<Slot, Car> parkingMap;
    public int parkingLotCapacity;
    public int numberOfParkingLots;
    public int numberOfSlotsPerLot;
    public int slotCounter = 0;
    Slot removeSlot = new Slot();
    List<Integer> unParkedSlots;

    public Attendant(int parkingLotCapacity, int numberOfParkingLots, int numberOfSlotsPerLot) {
        this.parkingLotCapacity = parkingLotCapacity;
        this.numberOfParkingLots = numberOfParkingLots;
        this.numberOfSlotsPerLot = numberOfSlotsPerLot;
        this.parkingMap = new HashMap<>();
        observersList = new ArrayList<>();
        unParkedSlots = new ArrayList<>();
    }

    public void addObserver(IParkingObserver... observers) {
        Collections.addAll(observersList, observers);
    }

    public Map<Slot, Car> park(Car car) {
        if (parkingMap.size() > this.parkingLotCapacity)
            throw new ParkingLotException(ParkingLotException.ExceptionType.LOT_FULL);
        Slot slot = new Slot(ParkingLotUtility.getCurrentTime());
        ParkingLot parkingLot;
        switch (car.getDriverType()) {
            case NORMAL_DRIVER:
                slotCounter++;
                if (car.getCarType().equals(CarType.LARGE_CAR)) {
                    slot.setSlotId(slotCounter);
                    parkingLot = new ParkingLot(ParkingLotUtility.assignLot(slot.getSlotId()));
                    slot.setLot(parkingLot);
                    if ((parkingLotCapacity / numberOfParkingLots) - slot.lot.getCarCounter() >= 2) {
                        parkingMap.put(slot, car);
                        break;
                    }
                    throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SPACE_FOR_LARGE_CAR);
                }
                slot.setSlotId(slotCounter);
                parkingLot = new ParkingLot(ParkingLotUtility.assignLot(slot.getSlotId()));
                slot.setLot(parkingLot);
                parkingMap.put(slot, car);
                break;
            case HANDICAP_DRIVER:
                unParkedSlots.sort(Comparator.comparing(Integer::intValue));
                if (unParkedSlots.size() != 0)
                    slot.setSlotId(unParkedSlots.get(0));
                else {
                    slotCounter++;
                    slot.setSlotId(slotCounter);
                }
                parkingLot = new ParkingLot(ParkingLotUtility.assignLot(slot.getSlotId()));
                slot.setLot(parkingLot);
                parkingMap.put(slot, car);
                break;
        }

        this.notifyToObserver(parkingMap.size());
        return parkingMap;
    }

    public Map<Slot, Car> unPark(Car car) throws ParkingLotException {
        if (!parkingMap.containsValue(car))
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        Set<Slot> slots = parkingMap.keySet();

        slots.stream()
                .filter(slot -> parkingMap.get(slot).equals(car))
                .forEachOrdered(slot -> removeSlot = slot);

        unParkedSlots.add(removeSlot.getSlotId());
        parkingMap.remove(removeSlot); // To Avoid ConcurrentModificationException
        this.notifyToObserver(parkingMap.size());
        return parkingMap;
    }

    @Override
    public void notifyToObserver(int totalCarsParked) {
        observersList.forEach(observer -> observer.sendParkingStatus(totalCarsParked, this.parkingLotCapacity));
    }
}
