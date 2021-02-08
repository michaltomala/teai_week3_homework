package pl.bykowski.week3.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class VehicleDTO {

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private String color;
}
