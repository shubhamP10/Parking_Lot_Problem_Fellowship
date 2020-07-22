package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.enums.Notifications;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.observer.AirportSecurity;
import com.bridgelabz.parkinglotmanagement.observer.Owner;
import com.bridgelabz.parkinglotmanagement.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

public class ParkingLotSystemManagementTest {

    ParkingLotSystem parkingLotSystem = null;
    Owner owner = null;
    AirportSecurity security = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(4,2);
        owner = new Owner();
        security = new AirportSecurity();
    }

    //    UC1
    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() {
        Car car = new Car("KA-48-S-8055");
        parkingLotSystem.parkVehicle(car);
        boolean isParked = parkingLotSystem.isParked(car);
        Assert.assertTrue(isParked);
    }

    //    UC2
    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnFalse() {
        Car car = new Car("KA-48-S-8055");
        parkingLotSystem.parkVehicle(car);
        parkingLotSystem.unParkVehicle(car);
        boolean isParked = parkingLotSystem.isParked(car);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            Car car = new Car("KA-48-S-8055");
            parkingLotSystem.parkVehicle(car);
            parkingLotSystem.unParkVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC3
    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformFull() {
        parkingLotSystem.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
    }

    //    UC4
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformLotFull() {
        parkingLotSystem.addObserver(owner, security);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        Car thirdCar = new Car("KA-01-S-1234");
        Car forthCar = new Car("KA-01-S-1234");
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.message);
    }

    @Test
    public void givenVehicleToPark_WhenMoreNumberOfVehicles_ShouldThrowException() {
        try {
            parkingLotSystem.addObserver(owner);
            Car firstCar = new Car("KA-48-S-8055");
            Car secondCar = new Car("KA-01-S-1234");
            Car thirdCar = new Car("KA-02-S-1234");
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.parkVehicle(secondCar);
            parkingLotSystem.parkVehicle(thirdCar);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }

    @Test
    public void givenVehicleToUnPark_WhenWrongVehicle_ShouldThrowException() {
        try {
            Car firstCar = new Car("KA-48-S-8055");
            Car secondCar = new Car("KA-01-S-8055");
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.unParkVehicle(secondCar);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC5
    @Test
    public void givenVehicleToPark_WhenHavingSpaceAfterUnPark_ShouldInformHaveSpaceToPark() {
        parkingLotSystem.addObserver(owner, security);
        Car car = new Car("KA-48-S-8055");
        Car car2 = new Car("KA-01-S-1234");
        parkingLotSystem.parkVehicle(car);
        parkingLotSystem.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.message);
        parkingLotSystem.unParkVehicle(car2);
        Assert.assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.message);
    }

    @Test
    public void givenVehicleToUnPark_WhenParkingLotIsEmpty_ShouldThrowException() {
        try {
            Car car = new Car("KA-48-S-8055");
            parkingLotSystem.unParkVehicle(car);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC7
    @Test
    public void givenVehicleToUnPark_WhenFindVehicle_ShouldReturnKey() {
        parkingLotSystem.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.unParkVehicle(secondCar);
        Assert.assertFalse(parkingLotSystem.isParked(secondCar));
    }

    //    UC8
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnParkedTime() {
        parkingLotSystem.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        parkingLotSystem.parkVehicle(firstCar);
        Timestamp parkedTime = firstCar.getParkedTime();
        System.out.println(parkedTime);
    }
}
