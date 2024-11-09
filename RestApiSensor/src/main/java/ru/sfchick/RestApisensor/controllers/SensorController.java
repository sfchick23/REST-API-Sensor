package ru.sfchick.RestApisensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sfchick.RestApisensor.dto.SensorDTO;
import ru.sfchick.RestApisensor.models.Sensor;
import ru.sfchick.RestApisensor.services.SensorServices;
import ru.sfchick.RestApisensor.util.sensorUtil.SensorErrorResponse;
import ru.sfchick.RestApisensor.util.sensorUtil.SensorNotCreateException;
import ru.sfchick.RestApisensor.util.sensorUtil.SensorNotFoundException;
import ru.sfchick.RestApisensor.util.sensorUtil.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorServices sensorServices;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorServices sensorServices, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorServices = sensorServices;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                     BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreateException(errors.toString());
        }

        sensorServices.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreateException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Sensor convertToSensor(@Valid SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }



}
