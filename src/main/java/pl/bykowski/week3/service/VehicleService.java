package pl.bykowski.week3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.entity.builder.VehicleBuilder;
import pl.bykowski.week3.exception.NotFoundException;
import pl.bykowski.week3.repository.VehicleRepository;
import pl.bykowski.week3.validator.VehicleValidator;

import java.util.Collections;
import java.util.List;


@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleValidator vehicleValidator;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleValidator vehicleValidator) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleValidator = vehicleValidator;
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
        if(!vehicleValidator.validateVehicleColor(color)) return Collections.emptyList();

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
        if(vehicleValidator.validateVehicleBrand(sourceVehicle))
            foundVehicle.setBrand(sourceVehicle.getBrand());
        if(vehicleValidator.validateVehicleModel(sourceVehicle))
            foundVehicle.setModel(sourceVehicle.getModel());
        if(vehicleValidator.validateVehicleColor(sourceVehicle))
            foundVehicle.setColor(sourceVehicle.getColor());
    }

    public void deleteVehicle(Integer vehicleId) {
        vehicleValidator.vehicleExists(vehicleId);
        vehicleRepository.deleteById(vehicleId);
    }

}
