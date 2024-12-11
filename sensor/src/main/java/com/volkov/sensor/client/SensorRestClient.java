package com.volkov.sensor.client;

import com.volkov.sensor.client.payload.RegisteredSensor;
import com.volkov.sensor.model.SensorDto;
import com.volkov.sensor.model.SensorRegistrationDto;

public interface SensorRestClient {

   RegisteredSensor registerSensor(SensorRegistrationDto sensor);

   void measurement(SensorDto measurementSensor);
}
