package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.AirportSecurity;
import com.bridgelabz.parkinglotmanagement.observer.Owner;
import com.bridgelabz.parkinglotmanagement.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotManagementTest {

    ParkingLot parkingLot = null;
    Owner owner = null;
    AirportSecurity security = null;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        owner = new Owner();
        security = new AirportSecurity();
    }

    //    UC1
    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() throws ParkingLotException {
        Car car = new Car("1", "KA-48-S-8055", 1);
        parkingLot.parkVehicle(car);
        String key = parkingLot.getVehicle(car);
        boolean isParked = parkingLot.isParked(key);
        Assert.assertTrue(isParked);
    }

    //    UC2
    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnFalse() throws ParkingLotException {
        Car car = new Car("1", "KA-48-S-8055", 1);
        parkingLot.parkVehicle(car);
        String key = parkingLot.getVehicle(car);
        parkingLot.unParkVehicle(key);
        boolean isParked = parkingLot.isParked(key);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            Car car = new Car("1", "KA-48-S-8055", 1);
            parkingLot.parkVehicle(car);
            parkingLot.unParkVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC3
    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformFull() throws ParkingLotException {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("1", "KA-48-S-8055", 1);
        Car secondCar = new Car("2", "KA-01-S-1234", 1);
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
    }

    //    UC4
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformLotFull() throws ParkingLotException {
        parkingLot.addObserver(owner);
        parkingLot.addObserver(security);
        Car firstCar = new Car("1", "KA-48-S-8055", 1);
        Car secondCar = new Car("2", "KA-01-S-1234", 1);
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
    }

    @Test
    public void givenVehicleToPark_WhenMoreNumberOfVehicles_ShouldThrowException() {
        try {
            parkingLot.addObserver(owner);
            Car firstCar = new Car("1", "KA-48-S-8055", 1);
            Car secondCar = new Car("2", "KA-01-S-1234", 1);
            Car thirdCar = new Car("3", "KA-02-S-1234", 1);
            parkingLot.parkVehicle(firstCar);
            parkingLot.parkVehicle(secondCar);
            parkingLot.parkVehicle(thirdCar);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }

    @Test
    public void givenVehicleToUnPark_WhenWrongVehicle_ShouldThrowException() {
        try {
            Car firstCar = new Car("1", "KA-48-S-8055", 1);
            Car secondCar = new Car("2", "KA-01-S-8055", 1);
            parkingLot.parkVehicle(firstCar);
            String key = parkingLot.getVehicle(secondCar);
            parkingLot.unParkVehicle(key);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC5
    @Test
    public void givenVehicleToPark_WhenHavingSpaceAfterUnPark_ShouldInformHaveSpaceToPark() throws ParkingLotException {
        parkingLot.addObserver(owner);
        parkingLot.addObserver(security);
        Car car = new Car("1", "KA-48-S-8055", 1);
        Car car2 = new Car("2", "KA-01-S-1234", 1);
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
        String key = parkingLot.getVehicle(car2);
        parkingLot.unParkVehicle(key);
        Assert.assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.getMessage());
    }

    @Test
    public void givenVehicleToUnPark_WhenParkingLotIsEmpty_ShouldThrowException() {
        try {
            Car car = new Car("1", "KA-48-S-8055", 1);
            String key = parkingLot.getVehicle(car);
            parkingLot.unParkVehicle(key);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC7
    @Test
    public void givenVehicleToUnPark_WhenFindVehicle_ShouldReturnKey() throws ParkingLotException {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("1", "KA-48-S-8055", 1);
        Car secondCar = new Car("2", "KA-01-S-1234", 1);
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        String key = parkingLot.getVehicle(secondCar);
        parkingLot.unParkVehicle(key);
        Assert.assertFalse(parkingLot.isParked(key));
    }

    @Test
    public void givenVehicleToPark_WhenBeforeUnParked_ShouldReturnBillAmount() throws ParkingLotException {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("1", "KA-48-S-8055", 12.5);
        parkingLot.parkVehicle(firstCar);
        double bill = parkingLot.generateBill(firstCar);
        Assert.assertEquals(125.0, bill, 0.0);
    }
}
