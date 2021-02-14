package pl.bykowski.week3.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bykowski.week3.dto.VehicleDTO;
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
        return ResponseEntity.ok(vehicleService.find(vehicleId));
    }

    @Override
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(String color) {
        return ResponseEntity.ok(vehicleService.findByColor(color));
    }

    @Override
    public ResponseEntity<Vehicle> addVehicle(VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(vehicleDTO));
    }

    @Override
    public ResponseEntity<Vehicle> updateVehicle(VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDTO));
    }

    @Override
    public ResponseEntity<Vehicle> editVehicle(Integer vehicleId, VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.editVehicle(vehicleId, vehicleDTO));
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(Integer vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
