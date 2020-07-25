package com.bridgelabz.parkinglotmanagement;

import com.bridgelabz.parkinglotmanagement.enums.CarColor;
import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.enums.DriverType;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParkingLotSystemManagementTest {

    ParkingLotSystem parkingLotSystem = null;
    Owner owner = null;
    AirportSecurity security = null;
    Car firstCar;
    Car secondCar;
    Car thirdCar;
    Car forthCar;
    Car fifthCar;
    Car sixthCar;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(4, 2);
        owner = new Owner();
        security = new AirportSecurity();
    }

    //    UC1
    @Test
    public void givenVehicle_WhenPark_ShouldReturnTrue() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        boolean isParked = parkingLotSystem.isParked(firstCar);
        Assert.assertTrue(isParked);
    }

    //    UC2
    @Test
    public void givenVehicleToPark_WhenUnParked_ShouldReturnFalse() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.unParkVehicle(firstCar);
        boolean isParked = parkingLotSystem.isParked(firstCar);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicleToUnPark_WhenNull_ShouldThrowException() {
        try {
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.unParkVehicle(null);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC3
    @Test
    public void givenVehicleToPark_WhenOwner_ShouldInformFull() {
        parkingLotSystem.addObserver(owner);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
    }

    //    UC4
    @Test
    public void givenVehicleToPark_WhenOwnerAndSecurity_ShouldInformLotFull() {
        parkingLotSystem.addObserver(owner, security);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.message);
    }

    @Test
    public void givenVehicleToPark_WhenMoreNumberOfVehicles_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.addObserver(owner);
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            forthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.parkVehicle(secondCar);
            parkingLotSystem.parkVehicle(thirdCar);
            parkingLotSystem.parkVehicle(forthCar);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }

    @Test
    public void givenVehicleToUnPark_WhenWrongVehicle_ShouldThrowException() {
        try {
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            secondCar = new Car("KA-01-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.unParkVehicle(secondCar);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    //    UC5
    @Test
    public void givenVehicleToPark_WhenHavingSpaceAfterUnPark_ShouldInformHaveSpaceToPark() {
        parkingLotSystem = new ParkingLotSystem(2, 2);
        parkingLotSystem.addObserver(owner, security);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, security.message);
        parkingLotSystem.unParkVehicle(secondCar);
        assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.message);
    }

    @Test
    public void givenVehicleToUnPark_WhenParkingLotIsEmpty_ShouldThrowException() {
        try {
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.unParkVehicle(firstCar);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenVehicleToUnPark_WhenNotFoundVehicle_ShouldReturnFalse() {
        parkingLotSystem.addObserver(owner);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.unParkVehicle(secondCar);
        Assert.assertFalse(parkingLotSystem.isParked(secondCar));
    }

    @Test
    public void givenVehicle_WhenParkedAtEveryLot_ShouldInformLotFull() {
        parkingLotSystem.addObserver(owner);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        assertEquals(Notifications.PARKING_LOT_IS_FULL.message, owner.message);
        parkingLotSystem.unParkVehicle(secondCar);
        assertEquals(Notifications.HAVE_SPACE_TO_PARK.message, owner.message);
    }

    //    UC8
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnParkedTime() {
        parkingLotSystem.addObserver(owner);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        Timestamp parkedTime = parkingLotSystem.getParkedTime(firstCar);
        System.out.println(parkedTime);
    }

    @Test
    public void givenVehiclesToPark_WhenParked_ShouldReturnCountOfTotalCarsParked() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        int count = parkingLotSystem.getCountOfVehiclesParked();
        assertEquals(4, count);
    }

    @Test
    public void givenVehiclesToPark_WhenSameVehicle_ShouldThrowException() {
        try {
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.parkVehicle(secondCar);
            parkingLotSystem.parkVehicle(firstCar);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.DUPLICATE_VEHICLE, e.type);
        }
    }

    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnCountOfParticularLot() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        long count = parkingLotSystem.getCarCountForEachLot(1);
        assertEquals(2, count);
    }

    @Test
    public void givenVehiclePark_WhenAskedToGetLocation_ShouldReturnLocationOfVehicle() {
        parkingLotSystem = new ParkingLotSystem(6, 4);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        fifthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        sixthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        parkingLotSystem.parkVehicle(fifthCar);
        parkingLotSystem.parkVehicle(sixthCar);
        String carLocation = parkingLotSystem.findCar(forthCar);
        assertEquals("Parking Lot : 4  Slot Number : 4", carLocation);
    }

    @Test
    public void givenVehicleToPark_WhenDriverIsHandicap_ShouldParkInNearestSlot() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1234", DriverType.HANDICAP_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.unParkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        assertEquals("Parking Lot : 1  Slot Number : 3", parkingLotSystem.findCar(forthCar));
    }

    @Test
    public void givenVehicleToPark_WhenAskedToGetParkedVehicles_ShouldReturnList() {
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        List<Integer> parkedSlots = parkingLotSystem.getParkedSlots();
        List<Integer> expectedList = Arrays.asList(1, 2, 3, 4);
        Assert.assertEquals(expectedList, parkedSlots);
    }

    @Test
    public void givenVehicle_WhenLargeVehicle_ShouldParkInHighestNumberOfFreeSpace() {
        parkingLotSystem = new ParkingLotSystem(6, 2);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.LARGE_CAR, CarColor.BLUE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        String carLocation = parkingLotSystem.findCar(thirdCar);
        assertEquals("Parking Lot : 1  Slot Number : 3", carLocation);
    }

    @Test
    public void givenLargeVehicleToPark_WhenNoSpace_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(4, 2);
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.LARGE_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.parkVehicle(secondCar);
            parkingLotSystem.parkVehicle(thirdCar);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.NO_SPACE_FOR_LARGE_CAR, e.type);
        }
    }

    @Test
    public void givenVehiclesToPark_WhenAskedForWhiteColor_ShouldReturnLocationOfAllWhiteCars() {
        parkingLotSystem = new ParkingLotSystem(4, 2);
        firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.WHITE);
        secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
        forthCar = new Car("KA-02-S-1222", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.WHITE);
        parkingLotSystem.parkVehicle(firstCar);
        parkingLotSystem.parkVehicle(secondCar);
        parkingLotSystem.parkVehicle(thirdCar);
        parkingLotSystem.parkVehicle(forthCar);
        List<String> carLocationByColor = parkingLotSystem.getCarLocationByColor(CarColor.WHITE);
        List<String> expectedList = Arrays.asList("Lot Number: 1 On Slot: 1", "Lot Number: 2 On Slot: 4");
        assertEquals(expectedList, carLocationByColor);
    }

    @Test
    public void givenVehiclesToPark_WhenThereIsNoWhiteColorCarsParked_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(4, 2);
            firstCar = new Car("KA-48-S-8055", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            secondCar = new Car("KA-01-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            thirdCar = new Car("KA-02-S-1234", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            forthCar = new Car("KA-02-S-1222", DriverType.NORMAL_DRIVER, CarType.SMALL_CAR, CarColor.BLUE);
            parkingLotSystem.parkVehicle(firstCar);
            parkingLotSystem.parkVehicle(secondCar);
            parkingLotSystem.parkVehicle(thirdCar);
            parkingLotSystem.parkVehicle(forthCar);
            parkingLotSystem.getCarLocationByColor(CarColor.WHITE);
        } catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, e.type);
        }
    }
}
