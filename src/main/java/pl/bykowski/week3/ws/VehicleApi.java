package pl.bykowski.week3.ws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bykowski.week3.entity.Vehicle;

import java.util.List;

public interface VehicleApi {

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles();

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable long id);

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(@PathVariable String color);

    @PostMapping
    public ResponseEntity<Void> addVehicle(@RequestBody Vehicle vehicle);

    @PutMapping
    public ResponseEntity<Void> updateVehicle(@RequestBody Vehicle vehicle);

    @PatchMapping("/{id}")
    public ResponseEntity<Void> editVehicle(@PathVariable long id, @RequestBody Vehicle vehicle);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable long id);

}
