package ru.sfchick.RestApisensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfchick.RestApisensor.models.Sensor;
import ru.sfchick.RestApisensor.repositories.SensorRepository;
import ru.sfchick.RestApisensor.util.sensorUtil.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorServices {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorServices(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
}
