package com.sysc3303.commons;

import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperty {
    private ConfigProperty(){
    };

    private static ConfigProperty instance;
    private Properties properties;

    public static ConfigProperty getInstance(){
        if (instance == null){
            try {
                InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
                Properties properties = new Properties();
                properties.load(inputStream);
                instance = new ConfigProperty();
                instance.properties = properties;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    };

    public String getProperty(String str){
        return properties.getProperty(str);
    }
}
