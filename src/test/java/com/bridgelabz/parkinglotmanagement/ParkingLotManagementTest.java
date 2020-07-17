package com.bridgelabz.parkinglotmanagement;

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
    }

    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() throws ParkingLotException {
        Car car = new Car("1","KA-48-S-8055");
        parkingLot.parkVehicle(car);
        boolean isParked = parkingLot.isParked(car);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleIfParked_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        Car car = new Car("1","KA-48-S-8055");
        parkingLot.parkVehicle(car);
        parkingLot.unParkVehicle(car);
        boolean isUnParked = parkingLot.isUnParked(car);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            Car car = new Car("1","KA-48-S-8055");
            parkingLot.parkVehicle(car);
            parkingLot.unParkVehicle(null);
        } catch (ParkingLotException e) {
            System.out.println(e.type);
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        }
    }

    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformInformLotFull() throws ParkingLotException {
        parkingLot.addMonitor(owner);
        Car car = new Car("1","KA-48-S-8055");
        Car car2 = new Car("2","KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
       Assert.assertEquals("Parking Lot Is Full", owner.getMessage());
    }
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformInformLotFull() throws ParkingLotException {
        parkingLot.addMonitor(owner);
        parkingLot.addMonitor(security);
        Car car = new Car("1","KA-48-S-8055");
        Car car2 = new Car("2","KA-01-S-1234");
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(car2);
        Assert.assertEquals("Parking Lot Is Full", owner.getMessage());
        Assert.assertEquals("Parking Lot Is Full", security.getMessage());
    }
}
