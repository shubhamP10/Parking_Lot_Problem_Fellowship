package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.model.Slot;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;
import com.bridgelabz.parkinglotmanagement.utility.ParkingLotUtility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParkingLotSystem {

    private Map<Slot, Car> parkingMap;
    Attendant attendant;

    public int numberOfSlotsPerLot;
    ParkingLotUtility parkingLotUtility;

    public ParkingLotSystem(int parkingLotCapacity, int numberOfParkingLots) {
        this.parkingLotUtility = new ParkingLotUtility(parkingLotCapacity, numberOfParkingLots);
        this.numberOfSlotsPerLot = parkingLotUtility.getNumberOfSlotsPerLot();
        this.attendant = new Attendant(parkingLotCapacity, numberOfParkingLots, parkingLotUtility.getNumberOfSlotsPerLot());
        this.parkingMap = new HashMap<>();
    }

    /**
     * Method To Park The Car.
     *
     * @param car Object
     * @throws ParkingLotException LOT FULL
     */
    public void parkVehicle(Car car) throws ParkingLotException {
        parkingMap = attendant.park(car);
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
     * @param car
     * @throws ParkingLotException NO SUCH VEHICLE
     */
    public void unParkVehicle(Car car) throws ParkingLotException {
        parkingMap = attendant.unPark(car);
    }

    /**
     * Method To Add Observers
     *
     * @param observers Interface
     */

    public void addObserver(IParkingObserver... observers) {
        attendant.addObserver(observers);
    }

    /**
     * Method to Check is Car Is Parked or Not.
     *
     * @return boolean value
     */
    public boolean isParked(Car car) {
        return parkingMap.containsValue(car);
    }
}
