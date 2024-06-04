package com.volkov.sensor;


import com.volkov.sensor.service.ConfigurationSensorService;
import com.volkov.sensor.service.SaveConfigException;
import com.volkov.sensor.service.impl.ConfigurationSensorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class SensorApplication {

    private static final Logger log = LoggerFactory.getLogger(SensorApplication.class);

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException,
            IllegalAccessException, SaveConfigException {
        log.info("Starting application Sensor");

        try{
            ConfigurationSensorService configurationSensorService = new ConfigurationSensorServiceImpl();
            if(!configurationSensorService.isRegistered()){
                log.info("now registered!");
            }
        } catch (Exception e) {

            log.info("Can not read https.strict parameter, leave default - ");
        }
        SpringApplication.run(SensorApplication.class,args);
    }
}
