package com.volkov.sensor.service;

import com.volkov.sensor.model.SensorDto;
import com.volkov.sensor.model.SensorRegistrationDto;

public interface SensorService {

    SensorDto sensorMeasurement();

    SensorRegistrationDto sensorRegistration() throws ServerException;

    boolean haveRegistration() throws Exception;
}
