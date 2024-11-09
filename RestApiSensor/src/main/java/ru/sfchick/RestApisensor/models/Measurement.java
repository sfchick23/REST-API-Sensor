package ru.sfchick.RestApisensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @DecimalMin(value = "-100.0", message = "Value must be greater than or equal to 0.0")
    @DecimalMax(value = "100.0", message = "Value must be less than or equal to 100.0")
    @Column(name = "value")
    private Double value;

    @NotNull(message = "raining should not be null")
    @Column(name = "raining")
    private Boolean raining;

    @Column(name = "measurement_time")
    private LocalDateTime measurementTime;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;


    public Boolean isRaining() {
        return raining;
    }


}
