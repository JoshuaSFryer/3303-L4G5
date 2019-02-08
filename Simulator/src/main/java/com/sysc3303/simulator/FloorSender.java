package com.sysc3303.simulator;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;

/**
 * Sends a message from the simulator to the floor
 * Singleton: Only one instance can exist
 *
 * @author Mattias Lightstone
 */
public class FloorSender {
    private static FloorSender instance;

    // load port number from prop file
    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));

    /**
     * Gets an instance of FloorSender
     * If there is already one return it
     * If there isn't construct a new one
     * @return an instance of FloorSender
     */
    public static FloorSender getInstance() {
        if (instance == null){
            instance = new FloorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    /**
     * Sends a floor button click message to the floor system
     * @param floor the floor that the button was pressed
     * @param direction the direction of the button that was pressed
     * @return true for successfull send
     */
    public boolean sendFloorClick(int floor, Direction direction){
        SimulatorMessageHandler.getInstance(simulatorPort).sendFloorButtonClickSimulation(floor, direction);
        return true;
    }

    /**
     * Singleton pattern uses private empty constructor
     */
    private FloorSender(){
    }
}
