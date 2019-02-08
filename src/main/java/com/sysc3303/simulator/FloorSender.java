package com.sysc3303.simulator;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;

public class FloorSender {
    private static FloorSender instance;

    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));

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
