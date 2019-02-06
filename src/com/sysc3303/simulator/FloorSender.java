package com.sysc3303.simulator;
//import com.sun.media.sound.WaveFloatFileReader;
import com.sysc3303.commons.Direction;
import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FloorSender {
    private static FloorSender instance;

    static int simulatorPort;

    static {
        Properties properties = new Properties();
        try{
            InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
            properties.loadFromXML(inputStream);

            simulatorPort = Integer.parseInt(properties.getProperty("simulatorPort"));
        }catch(FileNotFoundException e){
        }catch(IOException e){
        }
    }

    public static FloorSender getInstance() {
        if (instance == null){
            instance = new FloorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean sendFloorClick(int floor, Direction direction){
        SimulatorMessageHandler.getInstance(simulatorPort).sendFloorButtonClickSimulation(floor, direction);
        return true;
    }

    private FloorSender(){
    }
}
