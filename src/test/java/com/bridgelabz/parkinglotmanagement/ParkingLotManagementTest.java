package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
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
    public void givenVehicle_WhenPark_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.parkVehicle(vehicle);
        boolean parkingStatus = parkingLot.isParked();
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.parkVehicle(vehicle);
        parkingLot.unParkVehicle(vehicle);
        boolean parkingStatus = parkingLot.isUnParked();
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenVehicleIfParked_AndGivenDifferentVehicleToUnPark_ShouldThrowException() {
       try {
           parkingLot.parkVehicle(vehicle);
           parkingLot.isUnParked();
       } catch (ParkingLotException e){
           Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_MISMATCH);
       }

    }
}
