package com.sysc3303.simulator;

import com.sysc3303.commons.Direction;

/**
 * Defines the action to perform when the Simulator receives an event from the floor
 *
 * FloorReceiver is a Singleton
 */
public class FloorReceiver {
    private static FloorReceiver instance;

    /**
     * Gets an instance of FloorReceiver
     *
     * If there is an instance return it
     * If there isn't one construct it and return it
     *
     * @return an instance of FloorReceiver
     *
     */
    public static FloorReceiver getInstance() {
        if (instance == null){
            instance = new FloorReceiver();
        }
        return instance;
    }

    public static synchronized void setNull(){ instance = null;}

    /**
     * Called when an elevator arrival message is received from the floor
     * Forwards the message to the Triggered event map to send the corresponding events to an elevator
     * @param floor the floor of the arrived elevator
     * @param direction the direction of the arrived elevator
     * @param elevatorId the id of the arrived elevator
     */
    public void receiveElevatorArrival(int floor, Direction direction, int elevatorId){
        System.out.println("Elevator " + elevatorId + " has arrived at floor " + floor + " traveling " + direction);
        TriggeredEventMap.getInstance().send(floor, direction, elevatorId);
    }

    private FloorReceiver(){
    }
}
