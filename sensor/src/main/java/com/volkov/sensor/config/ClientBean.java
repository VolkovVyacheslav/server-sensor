package com.volkov.sensor.config;

import com.volkov.sensor.client.SensorRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBean {

    @Bean
    public SensorRestClientImpl sensorRestClient(@Value("${server.http.address:http://localhost:8083/sensors/}") String baseURL){
        return new SensorRestClientImpl(RestClient.builder()
                .baseUrl(baseURL)
                .build());
    }
}
