package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.utility.IParkingMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot implements IParkingLot {

    private final int PARKING_LOT_CAPACITY;
    private final List<IParkingMonitor> monitors = new ArrayList<>();
    private final Map<String, Car> parkingMap = new HashMap<>();

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
            parkingMap.put(car.getID(), car);
        } else if (this.parkingMap.size() == PARKING_LOT_CAPACITY) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.LOT_FULL);
        }
        if (this.parkingMap.size() == PARKING_LOT_CAPACITY) {
            this.notifyToMonitor(Notifications.PARKING_LOT_IS_FULL.message);
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
        this.notifyToMonitor(Notifications.HAVE_SPACE_TO_PARK.message);
    }

    /**
     * Method To Add Monitors
     *
     * @param monitor Interface
     */
    @Override
    public void addMonitor(IParkingMonitor monitor) {
        this.monitors.add(monitor);
    }

    /**
     * Method To Update Message To The Monitor
     */
    @Override
    public void notifyToMonitor(String message) {
        for (IParkingMonitor monitor : monitors) {
            monitor.updateMessage(message);
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

    /**
     * Method To check if Car is Un-Parked or Not.
     *
     * @return boolean value
     */
    @Override
    public boolean isUnParked(Car car) {
        return (!parkingMap.containsKey(car.getID()));
    }
}
