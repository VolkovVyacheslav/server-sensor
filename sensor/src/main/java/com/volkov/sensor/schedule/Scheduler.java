package com.volkov.sensor.schedule;


import com.volkov.sensor.client.SensorRestClient;
import com.volkov.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class Scheduler {

    private final SensorService sensorService;

    private final SensorRestClient sensorRestClient;

    @Scheduled(fixedDelay = 2000)
    public void measurement() throws Exception {
        if (!sensorService.haveRegistration()) {
            sensorService.sensorRegistration();
        } else {
            Thread.sleep((long) (Math.random() * 1000));
        }
        sensorRestClient.measurement(sensorService.sensorMeasurement());
        Thread.sleep((long) (Math.random() * 12000));
    }
}
