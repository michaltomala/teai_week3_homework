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

    public Vehicle find(long id) throws NotFoundException {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("There is no Vehicle with id: %s !!", id)));
    }

    public List<Vehicle> findByColor(String color) {
        if(StringUtils.isEmpty(color)) return Collections.emptyList();

        return vehicleRepository.findAllByColor(color);
    }

    public boolean isCorrect(Vehicle vehicle) {
        return vehicleValidator.valuesNotEmpty(vehicle);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) throws NotFoundException {
        Vehicle foundVehicle = find(vehicle.getId());

        foundVehicle.setBrand(vehicle.getBrand());
        foundVehicle.setModel(vehicle.getModel());
        foundVehicle.setColor(vehicle.getColor());

        vehicleRepository.save(foundVehicle);
    }

    public void editVehicle(long id, Vehicle vehicle) throws NotFoundException {
        Vehicle foundVehicle = find(id);
        fillWithNotEmptyValues(vehicle, foundVehicle);
        vehicleRepository.save(foundVehicle);
    }

    private void fillWithNotEmptyValues(Vehicle sourceVehicle, Vehicle foundVehicle) {
        if(vehicleValidator.valueNotEmpty(sourceVehicle.getBrand()))
            foundVehicle.setBrand(sourceVehicle.getBrand());
        if(vehicleValidator.valueNotEmpty(sourceVehicle.getBrand()))
            foundVehicle.setModel(sourceVehicle.getModel());
        if(vehicleValidator.valueNotEmpty(sourceVehicle.getColor()))
            foundVehicle.setColor(sourceVehicle.getColor());
    }

    public void deleteVehicle(long id) {
        vehicleRepository.deleteById(id);
    }

}
