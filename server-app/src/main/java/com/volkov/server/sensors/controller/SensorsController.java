package com.volkov.server.sensors.controller;


import com.volkov.server.sensors.controller.payload.MeasurementSensor;
import com.volkov.server.sensors.controller.payload.RegisteredSensor;
import com.volkov.server.sensors.controller.payload.RegistrationSensor;
import com.volkov.server.sensors.dto.CreateMeasurementsDto;
import com.volkov.server.sensors.dto.CreateSensorDto;
import com.volkov.server.sensors.dto.MeasurementsDto;
import com.volkov.server.sensors.dto.SensorDto;
import com.volkov.server.sensors.service.MeasurementsService;
import com.volkov.server.sensors.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("sensors")
public class SensorsController {

    private static final Logger log = LoggerFactory.getLogger(SensorsController.class);

    private static final String LOG_APPLICATION_LEVEL = "Server controllers Level: ";

    private final SensorService sensorService;

    private final MeasurementsService measurementsService;


    @PostMapping("/registration")
     public ResponseEntity<?> createSensors(@Valid @RequestBody RegistrationSensor payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if(bindingResult.hasErrors()){
            if (bindingResult instanceof BindException exception){
                throw exception;
            }else{
                throw new BindException(bindingResult);
            }
        }else{
            log.info(LOG_APPLICATION_LEVEL + "Have request for registered new sensor with name: " + payload.name());
            RegisteredSensor sensor = new RegisteredSensor(this.sensorService.registerSensor(payload).getId().toString());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("sensors/registration/")
                            .build(Map.of("sensorId", sensor.key())))
                    .body(sensor);
        }
    }

    @PostMapping("/{key}/measurements")
    public void measurements(@PathVariable(name ="key") String key,
                             @Valid @RequestBody MeasurementSensor measurementSensor,
                             BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()){
            if (bindingResult instanceof BindException exception){
                throw exception;
            }else{
                throw new BindException(bindingResult);
            }
        }else {
            CreateMeasurementsDto newMeasurement = CreateMeasurementsDto.builder()
                    .temp(measurementSensor.value())
                    .rain(measurementSensor.raining())
                    .sensor(SensorDto.builder().id(UUID.fromString(key)).build())
                    .build();
           MeasurementsDto measurements = measurementsService.measurement(newMeasurement);
            log.info(LOG_APPLICATION_LEVEL + "Have request of measurements for sensor with id:"
                    + measurements.getSensor().getId() + " at:"
                    + measurements.getTimeMeasurements() + " time, save");
        }
    }

    @GetMapping
    public List<CreateSensorDto> activeSensors()  {
        log.info(LOG_APPLICATION_LEVEL + "Have request about all active sensors");
            return this.sensorService.allActive();
    }

    @GetMapping("/{key}/measurements")
    public List<MeasurementsDto> measurementsForSensor(@PathVariable(name ="key") String key){

            log.info(LOG_APPLICATION_LEVEL + "Have request for returned list of last 20 measurements for sensor with id: " + key);
            return measurementsService.getMeasurementsBySensor(key);

    }

    @GetMapping("/measurements")
    public List<MeasurementsDto> actualMeasurements(){

        log.info(LOG_APPLICATION_LEVEL + "Have request for returned list of actual measurements ");
        OffsetDateTime dateTime = OffsetDateTime.now().minusMinutes(1L);
        return measurementsService.getMeasurementsAfterTimestamp(dateTime);

    }
}
