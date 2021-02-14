package pl.bykowski.week3.service;

import pl.bykowski.week3.dto.VehicleDTO;
import pl.bykowski.week3.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle find(Integer vehicleId);

    List<Vehicle> findByColor(String color);

    Vehicle addVehicle(VehicleDTO vehicleDTO);

    Vehicle updateVehicle(VehicleDTO vehicleDTO);

    Vehicle editVehicle(Integer vehicleId, VehicleDTO vehicleDTO);

    void deleteVehicle(Integer vehicleId);

}
