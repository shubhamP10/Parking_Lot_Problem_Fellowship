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
        boolean isParked = parkingLot.isParked();
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.parkVehicle(vehicle);
        parkingLot.unParkVehicle(vehicle);
        boolean isUnParked = parkingLot.isUnParked();
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicleIfParked_AndGivenDifferentVehicleToUnPark_ShouldThrowException() {
       try {
           parkingLot.parkVehicle(vehicle);
           parkingLot.unParkVehicle(vehicle2);
       } catch (ParkingLotException e){
           System.out.println(e.type);
           Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_MISMATCH);
       }
    }

    @Test
    public void givenVehicleToPark_IfLotFull_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle2);
        } catch (ParkingLotException e){
            System.out.println(e.type);
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.LOT_FULL);
        }
    }
}
