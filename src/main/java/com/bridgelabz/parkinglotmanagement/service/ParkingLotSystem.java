package com.bridgelabz.parkinglotmanagement.service;

import com.bridgelabz.parkinglotmanagement.enums.CarColor;
import com.bridgelabz.parkinglotmanagement.enums.CarCompany;
import com.bridgelabz.parkinglotmanagement.enums.CarType;
import com.bridgelabz.parkinglotmanagement.enums.DriverType;
import com.bridgelabz.parkinglotmanagement.exception.ParkingLotException;
import com.bridgelabz.parkinglotmanagement.model.Car;
import com.bridgelabz.parkinglotmanagement.model.Slot;
import com.bridgelabz.parkinglotmanagement.observer.IParkingObserver;
import com.bridgelabz.parkinglotmanagement.utility.ParkingLotUtility;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    public int numberOfSlotsPerLot;
    Attendant attendant;
    ParkingLotUtility parkingLotUtility;
    private Map<Slot, Car> parkingMap;

    public ParkingLotSystem(int parkingLotCapacity, int numberOfParkingLots) {
        this.parkingLotUtility = new ParkingLotUtility(parkingLotCapacity, numberOfParkingLots);
        this.numberOfSlotsPerLot = parkingLotUtility.getNumberOfSlotsPerLot();
        this.attendant = new Attendant(parkingLotCapacity, numberOfParkingLots, parkingLotUtility.getNumberOfSlotsPerLot());
        this.parkingMap = new HashMap<>();
    }

    /**
     * Method To Park The Car.
     *
     * @param car Object
     * @throws ParkingLotException LOT FULL
     */
    public void parkVehicle(Car car) throws ParkingLotException {
        parkingMap.keySet()
                .stream()
                .filter(slot -> parkingMap.get(slot).equals(car))
                .forEach(slot -> {
                    throw new ParkingLotException(ParkingLotException.ExceptionType.DUPLICATE_VEHICLE);
                });
        parkingMap = attendant.park(car);
    }

    /**
     * Method To Un-Park The Car.
     *
     * @param car
     * @throws ParkingLotException NO SUCH VEHICLE
     */
    public void unParkVehicle(Car car) throws ParkingLotException {
        parkingMap = attendant.unPark(car);
    }


    //Method To Add Observers
    public void addObserver(IParkingObserver... observers) {
        attendant.addObserver(observers);
    }

    //Method To Check If Vehicle is Parked Or Not
    public boolean isParked(Car car) {
        return parkingMap.containsValue(car);
    }

    //Method To Get Vehicle Parked Time
    public LocalDateTime getParkedTime(Car car) {
        return parkingMap.keySet()
                .stream()
                .filter(slot -> parkingMap.get(slot).equals(car))
                .findFirst()
                .map(Slot::getParkedTime)
                .orElse(null);
    }

    //Method TO Get Count Of Vehicles Parked
    public int getCountOfVehiclesParked() {
        return parkingMap.size();
    }

    //Method To Get Count Of Cars In Each Lot
    public long getCarCountForEachLot(int lotId) {
        return parkingMap.keySet().stream().filter(slot -> slot.lot.getLotId() == lotId).count();
    }

    //Method To Get Car Location
    public String findCar(Car car) {
        Slot slot = this.parkingMap.keySet().stream().filter(slot1 -> parkingMap.get(slot1).equals(car)).findFirst().get();
        return String.format("Parking Lot : %d  Slot Number : %d", slot.getLotId(), slot.getSlotId());
    }

    //Method To Get Parked Slots
    public List<Integer> getParkedSlots() {
        return parkingMap.keySet()
                .stream()
                .filter(slot -> slot.getSlotId() > 0)
                .map(Slot::getSlotId)
                .sorted(Comparator.comparing(Integer::intValue))
                .collect(Collectors.toList());
    }

    public List<String> getCarLocationByColor(CarColor carColor) {
        List<String> carLocationList = new ArrayList<>();
        List<Slot> slotList = this.parkingMap.keySet()
                .stream()
                .filter(slot -> slot.getCar().getCarColor().equals(carColor))
                .collect(Collectors.toList());
        slotList.forEach(slot -> carLocationList.add("Lot Number: " + slot.getLotId() + " On Slot: " + slot.getSlotId()));
        if (carLocationList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carLocationList;
    }

    public List<String> getCarLocationByColorAndCompanyName(CarColor carColor, CarCompany carCompany) {
        List<String> carLocationList = new ArrayList<>();
        List<Slot> slotList = this.parkingMap.keySet()
                .stream()
                .filter(slot -> slot.getCar().getCarColor().equals(carColor) && slot.getCar().getCarCompany().equals(carCompany))
                .collect(Collectors.toList());
        slotList.forEach(slot -> carLocationList.add("Lot Number: " + slot.getLotId()
                + " On Slot: " + slot.getSlotId()
                + " Plate Number: " + slot.getCar().getPlateNumber()));
        if (carLocationList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carLocationList;
    }

    public List<String> getCarLocationByCarCompany(CarCompany carCompany) {
        List<String> carLocationList = new ArrayList<>();
        List<Slot> slotList = this.parkingMap.keySet()
                .stream()
                .filter(slot -> slot.getCar().getCarCompany().equals(carCompany))
                .collect(Collectors.toList());
        slotList.forEach(slot -> carLocationList.add("Lot Number: " + slot.getLotId()
                + " On Slot: " + slot.getSlotId()));
        if (carLocationList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carLocationList;
    }

    public List<String> getAllCarsDetailsParkedWithinTimeRange(int minutes) {
        List<String> carDetailsList = new ArrayList<>();
        List<Slot> slotList = this.parkingMap.keySet()
                .stream()
                .filter(slot -> Duration.between(slot.getParkedTime(), LocalDateTime.now()).toMinutes() <= minutes)
                .collect(Collectors.toList());
        slotList.forEach(slot -> carDetailsList.add("Lot: " + slot.getLotId()
                + " Slot Number: " + slot.getSlotId()
                + " Plate Number: " + slot.getCar().getPlateNumber()));
        if (carDetailsList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carDetailsList;
    }

    public List<String> getDetailsOfAllSmallCarsByLotAndDriverType(int lotNumber, DriverType driverType) {
        List<String> carDetailsList = new ArrayList<>();
        List<Slot> slotList = this.parkingMap.keySet()
                .stream()
                .filter(slot -> slot.getCar().getDriverType().equals(driverType)
                        && slot.getLotId() == lotNumber
                        && slot.getCar().getCarType().equals(CarType.SMALL_CAR))
                .collect(Collectors.toList());
        slotList.forEach(slot -> carDetailsList.add("Lot: " + slot.getLotId()
                + " Slot Number: " + slot.getSlotId()
                + " Plate Number: " + slot.getCar().getPlateNumber()));
        if (carDetailsList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carDetailsList;
    }

    public List<String> getDetailsOfAllParkedCars() {
        List<String> carDetailsList = new ArrayList<>();
        List<Slot> slotList = new ArrayList<>(this.parkingMap.keySet());
        slotList.forEach(slot -> carDetailsList.add(slot.getCar().getPlateNumber()));
        if (carDetailsList.size() == 0)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
        return carDetailsList;
    }
}
