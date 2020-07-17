package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;

public class ParkingLot implements IParkingLot {

    private Object vehicle = null;

    /**
     * Method To Park The Car.
     *
     * @param vehicle Object
     * @throws ParkingLotException LOT FULL
     */
    @Override
    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.LOT_FULL);
        this.vehicle = vehicle;
    }

    /**
     * Method To Un-Park The Car.
     *
     * @param vehicle object
     * @throws ParkingLotException NO SUCH VEHICLE
     */
    @Override
    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("No Such Vehicle", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        if (this.vehicle != null && this.vehicle.equals(vehicle))
            this.vehicle = null;
    }

    /**
     * Method to Check is Car Is Parked or Not.
     *
     * @return boolean value
     */
    @Override
    public boolean isParked() {
        return this.vehicle != null;
    }

    /**
     * Method To check if Car is Un-Parked or Not.
     *
     * @return boolean value
     */
    @Override
    public boolean isUnParked() {
        return this.vehicle == null;
    }
}
