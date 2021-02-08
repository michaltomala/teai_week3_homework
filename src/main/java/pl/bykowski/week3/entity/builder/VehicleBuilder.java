package pl.bykowski.week3.entity.builder;

import pl.bykowski.week3.dto.VehicleDTO;
import pl.bykowski.week3.entity.Vehicle;

public class VehicleBuilder {

    public static Vehicle fromDTO(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(vehicleDTO.getBrand());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setColor(vehicleDTO.getColor());

        return vehicle;
    }

    /**
     * Metoda stworzona na potrzebę inicjalizacji danych (w realnych środowiskach jest to utylizacji)
     *
     */
    public static Vehicle create(String brand, String model, String color) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setColor(color);

        return vehicle;
    }

}
