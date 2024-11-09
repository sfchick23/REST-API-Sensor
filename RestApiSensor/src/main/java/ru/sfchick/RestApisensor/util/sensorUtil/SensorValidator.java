package ru.sfchick.RestApisensor.util.sensorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sfchick.RestApisensor.dto.SensorDTO;
import ru.sfchick.RestApisensor.models.Sensor;
import ru.sfchick.RestApisensor.services.SensorServices;

@Component
public class SensorValidator implements Validator {

    private final SensorServices services;

    @Autowired
    public SensorValidator(SensorServices services) {
        this.services = services;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        if (services.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Это имя сенсора уже есть в базе данных");
        }
    }
}
