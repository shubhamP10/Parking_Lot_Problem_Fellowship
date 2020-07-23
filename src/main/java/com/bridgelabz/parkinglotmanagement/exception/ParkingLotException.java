package com.bridgelabz.parkinglotmanagement.exception;

public class ParkingLotException extends RuntimeException {

    public ExceptionType type;

    public ParkingLotException(ExceptionType type) {
        super(type.message);
        this.type = type;
    }

    public enum ExceptionType {
        NO_SUCH_VEHICLE("No Such Vehicle"), VEHICLE_MISMATCH("Vehicle Mismatch"),
        LOT_FULL("Parking Lot Is Full"), PARKING_LOT_IS_EMPTY("Parking Lot is Empty"),
        DUPLICATE_VEHICLE("Duplicate Vehicle Parked");

        String message;

        ExceptionType(String message) {
            this.message = message;
        }
    }
}
