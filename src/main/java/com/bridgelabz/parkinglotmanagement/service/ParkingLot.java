package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.utility.IParkingMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot implements IParkingLot {

    private final int PARKING_LOT_CAPACITY = 2;
    private List<IParkingMonitor> monitors = new ArrayList<>();
    private Map<String, Car> parkingMap = new HashMap<>();

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
            throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.LOT_FULL);
        }
        if (this.parkingMap.size() == PARKING_LOT_CAPACITY) {
            this.notifyToMonitor("Parking Lot Is Full");
        }
    }

    private void notifyToMonitor(String message) {
        monitors.get(0).update(message);
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
            throw new ParkingLotException("No Such Vehicle", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        if (parkingMap.containsKey(car.getID()))
            parkingMap.remove(car.getID());
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

    public void addMonitor(IParkingMonitor monitor) {
        this.monitors.add(monitor);
    }
}
