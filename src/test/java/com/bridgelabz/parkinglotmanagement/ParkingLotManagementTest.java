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

import java.sql.Timestamp;

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
    public void givenVehicle_WhenPark_ShouldReturnTrue() {
        Car car = new Car("KA-48-S-8055");
        parkingLot.parkVehicle(car);
        String lotNumber = parkingLot.getVehicle(car);
        boolean isParked = parkingLot.isParked(lotNumber);
        Assert.assertTrue(isParked);
    }

    //    UC2
    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnFalse() {
        Car car = new Car("KA-48-S-8055");
        parkingLot.parkVehicle(car);
        String lotNumber = parkingLot.getVehicle(car);
        parkingLot.unParkVehicle(lotNumber);
        boolean isParked = parkingLot.isParked(lotNumber);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            Car car = new Car("KA-48-S-8055");
            parkingLot.parkVehicle(car);
            parkingLot.unParkVehicle(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC3
    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformFull() {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
    }

    //    UC4
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformLotFull() {
        parkingLot.addObserver(owner, security);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
    }

    @Test
    public void givenVehicleToPark_WhenMoreNumberOfVehicles_ShouldThrowException() {
        try {
            parkingLot.addObserver(owner);
            Car firstCar = new Car("KA-48-S-8055");
            Car secondCar = new Car("KA-01-S-1234");
            Car thirdCar = new Car("KA-02-S-1234");
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
            Car firstCar = new Car("KA-48-S-8055");
            Car secondCar = new Car("KA-01-S-8055");
            parkingLot.parkVehicle(firstCar);
            String key = parkingLot.getVehicle(secondCar);
            parkingLot.unParkVehicle(key);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC5
    @Test
    public void givenVehicleToPark_WhenHavingSpaceAfterUnPark_ShouldInformHaveSpaceToPark() {
        parkingLot.addObserver(owner, security);
        Car car = new Car("KA-48-S-8055");
        Car car2 = new Car("KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.getMessage());
        Assert.assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.getMessage());
        String lotNumber = parkingLot.getVehicle(car2);
        parkingLot.unParkVehicle(lotNumber);
        Assert.assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.getMessage());
    }

    @Test
    public void givenVehicleToUnPark_WhenParkingLotIsEmpty_ShouldThrowException() {
        try {
            Car car = new Car("KA-48-S-8055");
            String lotNumber = parkingLot.getVehicle(car);
            parkingLot.unParkVehicle(lotNumber);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC7
    @Test
    public void givenVehicleToUnPark_WhenFindVehicle_ShouldReturnKey() {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        Car secondCar = new Car("KA-01-S-1234");
        parkingLot.parkVehicle(firstCar);
        parkingLot.parkVehicle(secondCar);
        String lotNumber = parkingLot.getVehicle(secondCar);
        parkingLot.unParkVehicle(lotNumber);
        Assert.assertFalse(parkingLot.isParked(lotNumber));
    }

    //    UC8
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnParkedTime() {
        parkingLot.addObserver(owner);
        Car firstCar = new Car("KA-48-S-8055");
        parkingLot.parkVehicle(firstCar);
        Timestamp parkedTime = firstCar.getParkedTime();
        System.out.println(parkedTime);
    }
}
