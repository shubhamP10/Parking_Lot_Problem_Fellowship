package com.bridgelabz.parkinglotmanagement.exception;

public class ParkingLotException extends Throwable {

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        NO_SUCH_VEHICLE, VEHICLE_MISMATCH, LOT_FULL
    }


}
