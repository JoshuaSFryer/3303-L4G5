package com.sysc3303.simulator;

import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ElevatorSender {
    private static ElevatorSender instance;

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

    private ElevatorSender(){
    }


    public static ElevatorSender getInstance() {
        if (instance == null){
            instance = new ElevatorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean sendElevatorClick(int elevatorNum, int elevatorButton){
        SimulatorMessageHandler.getInstance(simulatorPort).sendElevatorButtonClickSimulation(elevatorButton, elevatorNum);
        return true;
    }
}
