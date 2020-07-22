package com.bridgelabz.parkinglotmanagement.observer;

public class Owner implements IParkingObserver {

    private String message;

    @Override
    public void updateMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
