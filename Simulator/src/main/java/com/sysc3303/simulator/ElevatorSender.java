package com.sysc3303.simulator;

import com.sysc3303.commons.ConfigProperties;

/**
 * @author Mattias Lightstone
 *
 */
public class ElevatorSender {
    private static ElevatorSender instance;

    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));

    /**
     * Singleton pattern requires private empty constructor
     */
    private ElevatorSender(){
    }

    /**
     * Calls the constructor if the first time called
     * Only allows one instance of ElevatorSender to exist at a time
     * @return
     */
    public static ElevatorSender getInstance() {
        if (instance == null){
            instance = new ElevatorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){ instance = null; }

    /**
     * Sends a simulated button press to the elevator system
     * @param elevatorId the identifier of the elevator
     * @param elevatorButton the button that is being pressed
     */
    public void sendElevatorClick(int elevatorId, int elevatorButton){
        SimulatorMessageHandler.getInstance(simulatorPort).sendElevatorButtonClickSimulation(elevatorButton, elevatorId);
    }

    public void sendDoorStick(int elevatorId, int numSecondsStuck){
        SimulatorMessageHandler.getInstance(simulatorPort).sendDoorStick(elevatorId, numSecondsStuck);
    }

    public void sendElevatorStick(int elevatorId, int numSecondsStuck){
        SimulatorMessageHandler.getInstance(simulatorPort).sendElevatorStick(elevatorId, numSecondsStuck);
    }
}
