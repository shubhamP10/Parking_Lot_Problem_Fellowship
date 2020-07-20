package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot implements IParkingLot {

    private final int PARKING_LOT_CAPACITY;
    private final List<IParkingObserver> observers = new ArrayList<>();
    private final Map<String, Car> parkingMap = new HashMap<>();
    Attendant attendant = new Attendant();

    public ParkingLot(int capacity) {
        this.PARKING_LOT_CAPACITY = capacity;
    }

    /**
     * Method To Park The Car.
     *
     * @param car Object
     * @throws ParkingLotException LOT FULL
     */
    @Override
    public void parkVehicle(Car car) throws ParkingLotException {
        if (this.parkingMap.size() < PARKING_LOT_CAPACITY) {
            String key = attendant.parkVehicle(parkingMap);
            parkingMap.put(key, car);
        } else {
            throw new ParkingLotException(ParkingLotException.ExceptionType.LOT_FULL);
        }
        if (this.parkingMap.size() == PARKING_LOT_CAPACITY) {
            this.notifyToObserver(Notifications.PARKING_LOT_IS_FULL.message);
        }
    }

    /**
     * Method To Un-Park The Car.
     *
     * @param car object
     * @throws ParkingLotException NO SUCH VEHICLE
     */
    @Override
    public void unParkVehicle(Car car) throws ParkingLotException {
        if (car == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        if (parkingMap.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_EMPTY);
        if (!parkingMap.containsKey(car.getID()))
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_MISMATCH);
        parkingMap.remove(car.getID());
        this.notifyToObserver(Notifications.HAVE_SPACE_TO_PARK.message);
    }

    /**
     * Method To Add Observers
     *
     * @param observer Interface
     */
    @Override
    public void addObserver(IParkingObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Method To Update Message To The Observer
     */
    @Override
    public void notifyToObserver(String message) {
        for (IParkingObserver observer : observers) {
            observer.updateMessage(message);
        }
    }

    /**
     * Method to Check is Car Is Parked or Not.
     *
     * @return boolean value
     */
    @Override
    public boolean isParked(Car car) {
        return parkingMap.containsKey(car.getID());
    }
}
