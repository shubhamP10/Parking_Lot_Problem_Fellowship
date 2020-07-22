package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;

import java.sql.Timestamp;
import java.util.*;

public class ParkingLot implements IParkingLot {

    private final int PARKING_LOT_CAPACITY;
    private final List<IParkingObserver> observersList = new ArrayList<>();
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
            String lotNumber = attendant.parkVehicle(parkingMap);
            car.setParkedTime(this.getCurrentTime());
            parkingMap.put(lotNumber, car);
        } else {
            throw new ParkingLotException(ParkingLotException.ExceptionType.LOT_FULL);
        }
        if (this.parkingMap.size() == PARKING_LOT_CAPACITY) {
            this.notifyToObserver(Notifications.PARKING_LOT_IS_FULL.message);
        }
    }

    //Method To Get Current Timestamp
    public Timestamp getCurrentTime() {
        //Date object
        Date date = new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        return new Timestamp(time);
    }

    /**
     * Method To Un-Park The Car.
     *
     * @param key
     * @throws ParkingLotException NO SUCH VEHICLE
     */
    @Override
    public void unParkVehicle(String key) throws ParkingLotException {
        if (key == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        if (parkingMap.containsKey(key)) {
            parkingMap.remove(key);
            notifyToObserver(Notifications.HAVE_SPACE_TO_PARK.message);
        }
    }

    /**
     * Method To Add Observers
     *
     * @param observers Interface
     */
    @Override
    public void addObserver(IParkingObserver... observers) {
        Collections.addAll(observersList, observers);
    }

    /**
     * Method To Update Message To The Observer
     */
    @Override
    public void notifyToObserver(String message) {
        observersList.forEach(observer -> observer.updateMessage(message));
    }

    /**
     * Method to Check is Car Is Parked or Not.
     *
     * @return boolean value
     */
    @Override
    public boolean isParked(String key) {
        return parkingMap.containsKey(key);
    }

    @Override
    public String getVehicle(Car car) {
        return parkingMap.keySet().stream().filter(key -> parkingMap.get(key) == car).findFirst().orElse(null);
    }
}
