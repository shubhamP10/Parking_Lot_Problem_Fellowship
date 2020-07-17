package com.bridgelabz.parkingmanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotManagementTest {

    ParkingLot parkingLot = null;
    Object vehicle = null;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot();
        vehicle = new Object();
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
}
