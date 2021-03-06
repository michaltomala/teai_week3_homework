package pl.bykowski.week3.ws;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bykowski.week3.dto.VehicleDTO;
import pl.bykowski.week3.entity.Vehicle;

import javax.validation.groups.Default;
import java.util.List;

public interface VehicleApi {

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles();

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer vehicleId);

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(@PathVariable String color);

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody @Validated({Default.class, PostMapping.class}) VehicleDTO vehicleDTO);

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody @Validated({Default.class, PutMapping.class}) VehicleDTO vehicleDTO);

    @PatchMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> editVehicle(@PathVariable Integer vehicleId, @RequestBody VehicleDTO vehicleDTO);

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer vehicleId);

}
