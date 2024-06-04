package com.volkov.sensor.service;

public interface ConfigurationSensorService {

    String getSensorUUID();

    void setSensorUUID(String id) throws SaveConfigException;

    String getSensorName();

    void setSensorName(String name) throws SaveConfigException;

    String getServerAddress();

    void setServerAddress(String address) throws SaveConfigException;

    void setRegistered(boolean registered) throws SaveConfigException ;

    void save() throws SaveConfigException;

    boolean isRegistered() throws Exception;

    void createProperties() throws SaveConfigException;

}
