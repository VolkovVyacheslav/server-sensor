package com.volkov.server.sensors.service;

import com.volkov.server.sensors.controller.payload.RegistrationSensor;
import com.volkov.server.sensors.dto.CreateSensorDto;
import com.volkov.server.sensors.dto.SensorDto;

import java.util.List;
import java.util.UUID;

public interface SensorService {

 CreateSensorDto registerSensor(RegistrationSensor sensor);

 SensorDto isActive(SensorDto sensor);

 List<CreateSensorDto> allActive();

 void isOffline(List<UUID> disableSensorsId);
}
