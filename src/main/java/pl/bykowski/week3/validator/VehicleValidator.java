package pl.bykowski.week3.validator;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bykowski.week3.entity.Vehicle;
import pl.bykowski.week3.repository.VehicleRepository;

import java.util.Objects;

@Component
public class VehicleValidator {

    public boolean valuesNotEmpty(Vehicle vehicle) {
        return Objects.nonNull(vehicle.getId()) &&
                StringUtils.isNoneEmpty(vehicle.getBrand(), vehicle.getModel(), vehicle.getColor());
    }

    public boolean valueNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }


}
