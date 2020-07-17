package com.bridgelabz.parkingmanagement;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotManagementTest {

    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        boolean parkingStatus = parkingLot.getParkingStatus(new Object());
        Assert.assertTrue(parkingStatus);
    }
}
