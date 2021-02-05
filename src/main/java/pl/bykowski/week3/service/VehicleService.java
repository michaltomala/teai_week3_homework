package pl.bykowski.week3.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.entity.builder.VehicleBuilder;
import pl.bykowski.week3.exception.NotFoundException;
import pl.bykowski.week3.repository.VehicleRepository;

import java.util.Collections;
import java.util.List;


@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        vehicleRepository.save(VehicleBuilder.create("BMW", "M5", "Blue"));
        vehicleRepository.save(VehicleBuilder.create("Audi", "A6", "Red"));
        vehicleRepository.save(VehicleBuilder.create("Ford", "Mustang", "White"));
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle find(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NotFoundException(String.format("There is no Vehicle with id: %s !!", vehicleId)));
    }

    public List<Vehicle> findByColor(String color) {
        if(StringUtils.isEmpty(color)) return Collections.emptyList();

        return vehicleRepository.findAllByColor(color);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) {
        Vehicle foundVehicle = find(vehicle.getId());

        foundVehicle.setBrand(vehicle.getBrand());
        foundVehicle.setModel(vehicle.getModel());
        foundVehicle.setColor(vehicle.getColor());

        vehicleRepository.save(foundVehicle);
    }

    public void editVehicle(Integer vehicleId, Vehicle vehicle) {
        Vehicle foundVehicle = find(vehicleId);
        fillWithNotEmptyValues(vehicle, foundVehicle);
        vehicleRepository.save(foundVehicle);
    }

    private void fillWithNotEmptyValues(Vehicle sourceVehicle, Vehicle foundVehicle) {
        if(StringUtils.isNotEmpty(sourceVehicle.getBrand()))
            foundVehicle.setBrand(sourceVehicle.getBrand());
        if(StringUtils.isNotEmpty(sourceVehicle.getBrand()))
            foundVehicle.setModel(sourceVehicle.getModel());
        if(StringUtils.isNotEmpty(sourceVehicle.getColor()))
            foundVehicle.setColor(sourceVehicle.getColor());
    }

    public void deleteVehicle(Integer vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

}
