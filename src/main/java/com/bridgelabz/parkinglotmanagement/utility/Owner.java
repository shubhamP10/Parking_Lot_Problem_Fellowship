package com.bridgelabz.parkinglotmanagement.utility;

public class Owner implements IParkingMonitor{
    private String message;

    @Override
    public void update(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
