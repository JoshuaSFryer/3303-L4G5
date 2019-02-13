package com.sysc3303.communication;

/**
 * Message from Scheduler to Elevator
 * Tells the elevator to go to a specific floor now
 *
 * Contains the destination floor
 *
 * @author Mattias Lightstone
 */
@SuppressWarnings("serial")
public class GoToFloorMessage extends Message{
    private final int destinationFloor;

    public GoToFloorMessage(int destinationFloor) {
        // Opcode for this message is 2
        super(OpCodes.GO_TO_FLOOR.getOpCode());
        this.destinationFloor = destinationFloor;
    }

    public String toString(){
        return "Go To Floor Message\n\tDestination Floor: " +
        destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}