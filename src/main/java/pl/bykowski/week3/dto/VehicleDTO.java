package pl.bykowski.week3.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Data
public class VehicleDTO {

    @Null(groups = PostMapping.class)
    @NotNull(groups = PutMapping.class)
    private Integer id;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private String color;
}
