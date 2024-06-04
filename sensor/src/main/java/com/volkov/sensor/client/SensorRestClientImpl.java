package com.volkov.sensor.client;

import com.volkov.sensor.client.payload.MeasurementSensor;
import com.volkov.sensor.client.payload.RegisteredSensor;
import com.volkov.sensor.client.payload.RegistrationSensor;
import com.volkov.sensor.model.SensorDto;
import com.volkov.sensor.model.SensorRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class SensorRestClientImpl implements SensorRestClient {

    private final RestClient restClient;

    @Override
    public RegisteredSensor registerSensor(SensorRegistrationDto sensor) {
        RegistrationSensor newSensor = new RegistrationSensor(sensor.getName());
        return this.restClient
                .post()
                .uri("registration")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newSensor)
                .retrieve()
                .body(RegisteredSensor.class);
    }

    @Override
    public void measurement(SensorDto measurementSensor) {
       MeasurementSensor measurement = new MeasurementSensor(measurementSensor.getTemperature(),
               measurementSensor.getRain());
       String uri = measurementSensor.getId() + "/measurements";
       this.restClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(measurement)
                .retrieve()
                .toBodilessEntity();
    }
}
