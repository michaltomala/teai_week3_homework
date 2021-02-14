package pl.bykowski.week3.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bykowski.week3.exception.NotFoundException;
import pl.bykowski.week3.repository.VehicleRepository;

@Component
public class VehicleValidator {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleValidator(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void vehicleExists(Integer vehicleId) {
        if(!vehicleRepository.existsById(vehicleId))
            throw new NotFoundException(String.format("There is no Vehicle with id: %s !!", vehicleId));
    }

}
