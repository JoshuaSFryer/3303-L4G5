package com.sysc3303.commons;

import java.util.Date;

/**
 * Message sent from an Elevator to the scheduler
 * Indicates that an elevator button has been pressed
 * Contains the floor button clicked, the elevator that it was clicked in and the time
 */
public class ElevatorButtonMessage extends Message{
    private final int destinationFloor;
    private final int elevatorId;
    private final Date time;

    public ElevatorButtonMessage(int destinationFloor, int elevatorId, Date time) {
        // the opcode for this message type is 5
        super(OpCodes.ELEVATOR_BUTTON.getOpCode());
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
        this.time = time;
    }

    public String toString(){
        return "Elevator Button Request\n\tDesination Floor: " +
                destinationFloor +"\n\tElevator Id: " + elevatorId +
                "\n\tTime: " + time;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public Date getTime() {
        return time;
    }
}