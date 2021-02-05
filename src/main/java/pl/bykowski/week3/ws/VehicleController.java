package pl.bykowski.week3.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.service.VehicleService;

import java.util.List;

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
    public ResponseEntity<Vehicle> getVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleService.find(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    @Override
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(String color) {
        return ResponseEntity.ok(vehicleService.findByColor(color));
    }

    @Override
    public ResponseEntity<Void> addVehicle(@Validated Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateVehicle(@Validated Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> editVehicle(Integer vehicleId, Vehicle vehicle) {
        vehicleService.editVehicle(vehicleId, vehicle);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(Integer vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
