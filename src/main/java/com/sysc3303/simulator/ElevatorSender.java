package com.sysc3303.simulator;

import com.sysc3303.commons.ConfigProperties;

public class ElevatorSender {
    private static ElevatorSender instance;

    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));

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
