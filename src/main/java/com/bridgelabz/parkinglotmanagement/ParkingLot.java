package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;

public class ParkingLot {
    private Object vehicle = null;

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.LOT_FULL);
        this.vehicle = vehicle;
    }

    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("No Such Vehicle", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        if (this.vehicle != null && this.vehicle.equals(vehicle))
            this.vehicle = null;
        throw new ParkingLotException("Vehicle Mismatch", ParkingLotException.ExceptionType.VEHICLE_MISMATCH);
    }

    public boolean isParked(){
        if (this.vehicle != null)
            return true;
        return false;
    }

    public boolean isUnParked() {
        if (this.vehicle == null) {
            return true;
        }
        return false;
    }
}
