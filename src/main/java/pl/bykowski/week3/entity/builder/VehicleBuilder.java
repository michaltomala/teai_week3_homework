package pl.bykowski.week3.entity.builder;

import pl.bykowski.week3.entity.Vehicle;

public class VehicleBuilder {

    public static Vehicle create(String brand, String model, String color) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setColor(color);

        return vehicle;
    }

}
