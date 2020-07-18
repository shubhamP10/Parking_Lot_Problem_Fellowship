package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.service.ParkingLot;
import com.bridgelabz.parkinglotmanagement.utility.AirportSecurity;
import com.bridgelabz.parkinglotmanagement.utility.Owner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotManagementTest {

    ParkingLot parkingLot = null;
    Owner owner = null;
    AirportSecurity security = null;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
        owner = new Owner();
        security = new AirportSecurity();
    }

//    UC1
    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() throws ParkingLotException {
        Car car = new Car("1", "KA-48-S-8055");
        parkingLot.parkVehicle(car);
        boolean isParked = parkingLot.isParked(car);
        Assert.assertTrue(isParked);
    }

//    UC2
    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        Car car = new Car("1", "KA-48-S-8055");
        parkingLot.parkVehicle(car);
        parkingLot.unParkVehicle(car);
        boolean isUnParked = parkingLot.isUnParked(car);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            Car car = new Car("1", "KA-48-S-8055");
            parkingLot.parkVehicle(car);
            parkingLot.unParkVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC3
    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformInformLotFull() throws ParkingLotException {
        parkingLot.addMonitor(owner);
        Car car = new Car("1", "KA-48-S-8055");
        Car car2 = new Car("2", "KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
    }

//    UC4
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformLotFull() throws ParkingLotException {
        parkingLot.addMonitor(owner);
        parkingLot.addMonitor(security);
        Car car = new Car("1", "KA-48-S-8055");
        Car car2 = new Car("2", "KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
    }

    @Test
    public void givenVehicleToPark_WhenMoreNumberOfVehicles_ShouldThrowException() {
        try {
            parkingLot.addMonitor(owner);
            Car car = new Car("1", "KA-48-S-8055");
            Car car2 = new Car("2", "KA-01-S-1234");
            Car car3 = new Car("3", "KA-02-S-1234");
            parkingLot.parkVehicle(car);
            parkingLot.parkVehicle(car2);
            parkingLot.parkVehicle(car3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }

    @Test
    public void givenVehicleToUnPark_WhenWrongVehicle_ShouldThrowException() {
        try {
            Car car = new Car("1", "KA-48-S-8055");
            Car car2 = new Car("2", "KA-01-S-8055");
            parkingLot.parkVehicle(car);
            parkingLot.unParkVehicle(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_MISMATCH, e.type);
        }
    }

    //    UC5
    @Test
    public void givenVehicleToPark_WhenHavingSpaceAfterUnPark_ShouldInformHaveSpaceToPark() throws ParkingLotException {
        parkingLot.addMonitor(owner);
        parkingLot.addMonitor(security);
        Car car = new Car("1", "KA-48-S-8055");
        Car car2 = new Car("2", "KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
        parkingLot.unParkVehicle(car2);
        Assert.assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.getMessage());
    }
}
