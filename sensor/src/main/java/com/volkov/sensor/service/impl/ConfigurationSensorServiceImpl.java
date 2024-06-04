package com.volkov.sensor.service.impl;


import com.volkov.sensor.service.ConfigurationSensorService;
import com.volkov.sensor.service.SaveConfigException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class ConfigurationSensorServiceImpl implements ConfigurationSensorService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationSensorServiceImpl.class);


    private static final String PREF_KEY_SERVER_ADDRESS = "server.http.address";

    private static final String PREF_KEY_SENSOR_UUID = "sensor.uuid";

    private static final String PREF_KEY_NAME_SENSOR = "sensor.name";

    private static final String PREF_KEY_REGISTER_SENSOR = "sensor.register";

    private static final String CONFIG_FILE_PATH = "sensor.properties";
    private Properties prop = new Properties();

    public ConfigurationSensorServiceImpl() throws SaveConfigException {
        InputStream input = null;

        try {
            input = new FileInputStream(CONFIG_FILE_PATH);
            prop.load(input);
        } catch (IOException io) {
            log.error("Could not load config " + CONFIG_FILE_PATH, io);
            log.info("Try create properties file");
            createProperties();
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    @Override
    public String getSensorUUID() {
        try {
            return prop.getProperty(PREF_KEY_SENSOR_UUID, "");
        } catch (Exception e){
            log.info("NO UUID params, first start? try register");
            return "";
        }
    }

    @Override
    public void setSensorUUID(String id) throws SaveConfigException {
     prop.put(PREF_KEY_SENSOR_UUID, id);
      save();
    }

    @Override
    public String getSensorName() {
        return prop.getProperty(PREF_KEY_NAME_SENSOR , "no name");
    }

    @Override
    public void setSensorName(String name) throws SaveConfigException {
      prop.put(PREF_KEY_NAME_SENSOR, name != null ? name : "no name");
      save();
    }

    @Override
    public String getServerAddress() {
        return prop.getProperty(PREF_KEY_SERVER_ADDRESS, "https://127.0.0.1:8083");
    }

    @Override
    public void setServerAddress(String address) throws SaveConfigException {
        prop.put(PREF_KEY_SERVER_ADDRESS, address != null ? address : "http://127.0.0.1:8083/sensors/");
        save();
    }

    @Override
    public void setRegistered(boolean registered) throws SaveConfigException  {
        prop.put(PREF_KEY_REGISTER_SENSOR, registered ? "true" : "false");
        save();
    }

    @Override
    public void save() throws SaveConfigException {
        OutputStream outputStream = null;
        try{
            outputStream =  new FileOutputStream(CONFIG_FILE_PATH);
            prop.store(outputStream, null);
        } catch (IOException e){
            log.error("Error! save configuration to " + CONFIG_FILE_PATH, e);
            throw new SaveConfigException();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    @Override
    public boolean isRegistered() throws Exception{
        InputStream is = new FileInputStream(new File(CONFIG_FILE_PATH));
        Properties props = new Properties();
        props.load(is);
        is.close();
        String str = props.getProperty("sensor.register");
        return Boolean.parseBoolean(str);
    }

    @Override
    public void createProperties() throws SaveConfigException {
        setSensorName("no name");
        setServerAddress("http://localhost:8083/sensors/");
        setSensorUUID("");
        setRegistered(false);
    }
}
