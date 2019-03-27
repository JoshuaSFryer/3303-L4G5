package com.sysc3303.commons;

import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * loads properties from config file
 * Singleton: Only one instance of ConfigProperties can exist
 */
public class ConfigProperties {
    /**
     * Single pattern uses private empty constructor
     */
    private ConfigProperties(){
    };

    private static ConfigProperties instance;
    private Properties properties;

    /**
     * Gets an instance of ConfigProperties
     * If there is not instance, construct a new one
     * If there is one return the existing one
     * @return an instance of ConfigProperties
     */
    public static ConfigProperties getInstance(){
        if (instance == null){
            try {
                InputStream inputStream = ConfigProperties.class.getResourceAsStream(Constants.CONFIG_PATH);
                Properties properties = new Properties();
                properties.load(inputStream);
                instance = new ConfigProperties();
                instance.properties = properties;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    };

    /**
     * Gets a property from the config properties file
     * @param str the property key
     * @return the string value in the property key value pair
     */
    public String getProperty(String str){
        return properties.getProperty(str);
    }

    public void setProperty(String key, String value){
        properties.setProperty(key, value);
    }
}
