package pl.bykowski.week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bykowski.week3.entity.Vehicle;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByColorIgnoreCase(String color);
}

