package com.volkov.sensor.service.impl;

import com.volkov.sensor.client.SensorRestClient;
import com.volkov.sensor.client.payload.RegisteredSensor;
import com.volkov.sensor.model.SensorDto;
import com.volkov.sensor.model.SensorRegistrationDto;
import com.volkov.sensor.service.ConfigurationSensorService;
import com.volkov.sensor.service.SaveConfigException;
import com.volkov.sensor.service.SensorService;
import com.volkov.sensor.service.ServerException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private static final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    private final ConfigurationSensorService configurationSensorService;

    private final SensorRestClient sensorRestClient;
    @Override
    public SensorDto sensorMeasurement() {
        log.info("Now sensor with name:{} and ID:{} sending measurements data", configurationSensorService.getSensorName(), configurationSensorService.getSensorUUID());
        return SensorDto.builder()
                .id(configurationSensorService.getSensorUUID())
                .temperature(Math.random()*50)
                .rain(isRain())
                .build();
    }

    @Override
    public SensorRegistrationDto sensorRegistration() throws ServerException {
        SensorRegistrationDto sensor = new SensorRegistrationDto();
        sensor.setName(configurationSensorService.getSensorName());
        try {
            RegisteredSensor registeredSensor = sensorRestClient.registerSensor(sensor);
            if (null == registeredSensor.key() || registeredSensor.key().isEmpty()) {
                throw new ServerException();
            }
            sensor.setUuid(registeredSensor.key());
            log.info("now sensor {} is registered with UUID:{}", sensor.getName(), sensor.getUuid());
        }catch (Exception e){
            log.error("Server is not connected!");
            throw new ServerException();
        }

        try {
            configurationSensorService.setSensorUUID(sensor.getUuid());
            configurationSensorService.setRegistered(true);
        } catch (SaveConfigException e) {
            throw new RuntimeException(e);
        }
        return sensor;
    }

    @Override
    public boolean haveRegistration() throws Exception {
        return configurationSensorService.isRegistered();
    }

    private Boolean isRain(){
        return Math.random() < 0.5 ? true : false;
    }
}
