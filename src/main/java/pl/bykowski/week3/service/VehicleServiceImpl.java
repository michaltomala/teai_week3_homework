package pl.bykowski.week3.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.bykowski.week3.dto.VehicleDTO;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.entity.builder.VehicleBuilder;
import pl.bykowski.week3.exception.NotFoundException;
import pl.bykowski.week3.repository.VehicleRepository;
import pl.bykowski.week3.validator.Validator;
import pl.bykowski.week3.validator.VehicleValidator;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleValidator vehicleValidator;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleValidator vehicleValidator) {
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
        if(!Validator.validateString(color)) return Collections.emptyList();

        return vehicleRepository.findAllByColorContainingIgnoreCase(color);
    }

    public Vehicle addVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = VehicleBuilder.fromDTO(vehicleDTO);
        vehicleRepository.save(vehicle);

        log.info("Vehicle added: {}", vehicle);
        return vehicle;
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle foundVehicle = find(vehicle.getId());

        foundVehicle.setBrand(vehicle.getBrand());
        foundVehicle.setModel(vehicle.getModel());
        foundVehicle.setColor(vehicle.getColor());
        vehicleRepository.save(foundVehicle);

        log.info("Vehicle updated: {}", foundVehicle);
        return foundVehicle;
    }

    public Vehicle editVehicle(Integer vehicleId, VehicleDTO vehicleDTO) {
        Vehicle vehicle = find(vehicleId);

        fillWithNotEmptyValues(vehicleDTO, vehicle);
        vehicleRepository.save(vehicle);

        log.info("Vehicle edited: {}", vehicle);
        return vehicle;
    }

    private void fillWithNotEmptyValues(VehicleDTO vehicleDTO, Vehicle foundVehicle) {
        if(Validator.validateString(vehicleDTO.getBrand()))
            foundVehicle.setBrand(vehicleDTO.getBrand());
        if(Validator.validateString(vehicleDTO.getModel()))
            foundVehicle.setModel(vehicleDTO.getModel());
        if(Validator.validateString(vehicleDTO.getColor()))
            foundVehicle.setColor(vehicleDTO.getColor());
    }

    public void deleteVehicle(Integer vehicleId) {
        vehicleValidator.vehicleExists(vehicleId);
        vehicleRepository.deleteById(vehicleId);
        log.info("Vehicle deleted: {}", vehicleId);
    }

}
