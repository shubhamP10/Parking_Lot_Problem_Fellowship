package com.bridgelabz.parkinglotmanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotManagementTest {

    ParkingLot parkingLot = null;
    Object vehicle = null;
    Object vehicle2 = null;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
        vehicle = new Object();
        vehicle2 = new Object();
    }

    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() {
        boolean parkingStatus = parkingLot.parkVehicle(vehicle);
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        parkingLot.parkVehicle(vehicle);
        boolean parkingStatus = parkingLot.parkVehicle(vehicle);
        Assert.assertFalse(parkingStatus);
    }

    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkVehicle(vehicle);
        boolean parkingStatus = parkingLot.unParkVehicle(vehicle);
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenVehicleIfParked_AndGivenDifferentVehicleToUnPark_ShouldReturnFalse() {
        parkingLot.parkVehicle(vehicle);
        boolean parkingStatus = parkingLot.unParkVehicle(vehicle2);
        Assert.assertFalse(parkingStatus);
    }
}
