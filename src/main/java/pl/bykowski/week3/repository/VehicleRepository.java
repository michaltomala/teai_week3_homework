package pl.bykowski.week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.exception.NotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByColor(String color);
}

