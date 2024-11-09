package ru.sfchick.RestApisensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfchick.RestApisensor.models.Measurement;
import ru.sfchick.RestApisensor.models.Sensor;
import ru.sfchick.RestApisensor.repositories.MeasurementRepository;
import ru.sfchick.RestApisensor.util.measurementUtil.MeasurementNotCreateException;
import ru.sfchick.RestApisensor.util.measurementUtil.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorServices sensorServices;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorServices sensorServices) {
        this.measurementRepository = measurementRepository;
        this.sensorServices = sensorServices;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findById(int id) {
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Measurement measurement) {
        // Пытаемся найти сенсор по имени
        Optional<Sensor> existingSensor = sensorServices.findByName(measurement.getSensor().getName());

        if (existingSensor.isPresent()) {
            // Если сенсор существует, привязываем его к измерению
            measurement.setSensor(existingSensor.get());
        } else {
            // Если сенсор не найден, выбрасываем исключение
            throw new MeasurementNotCreateException("Sensor with name " + measurement.getSensor().getName() + " not found");
        }

        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setMeasurementTime(LocalDateTime.now());
    }
}
