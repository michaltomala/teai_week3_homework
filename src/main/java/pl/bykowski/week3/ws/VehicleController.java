package pl.bykowski.week3.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.exception.NotFoundException;
import pl.bykowski.week3.service.VehicleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/vehicles", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
})
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public ResponseEntity<List<Vehicle>> getVehicles() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @Override
    public ResponseEntity<Vehicle> getVehicle(long id) {
        try {
            Vehicle vehicle = vehicleService.find(id);
            return ResponseEntity.ok(vehicle);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(String color) {
        return ResponseEntity.ok(vehicleService.findByColor(color));
    }

    @Override
    public ResponseEntity<Void> addVehicle(@Validated Vehicle vehicle) {
        if(vehicleService.isCorrect(vehicle)) {
            vehicleService.addVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Void> updateVehicle(@Validated Vehicle vehicle) {
        try {
            vehicleService.updateVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> editVehicle(long id, Vehicle vehicle) {
        try {
            vehicleService.editVehicle(id, vehicle);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
