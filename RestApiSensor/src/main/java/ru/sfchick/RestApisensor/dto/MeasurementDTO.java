package ru.sfchick.RestApisensor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {


    @DecimalMin(value = "-100.0", message = "Value must be greater than or equal to 0.0")
    @DecimalMax(value = "100.0", message = "Value must be less than or equal to 100.0")
    private Double value;

    @NotNull(message = "raining should not be null")
    private Boolean raining;

    @NotNull(message = "sensor should not be null")
    private SensorDTO sensor;


}
