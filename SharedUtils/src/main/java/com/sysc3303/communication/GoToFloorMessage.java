package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private final int elevatorId;

    /**
     * Constructor
     * @param destinationFloor the floor that the elevator should go to
     * @param elevatorId the elevator that should go to the destination floor
     */
    @JsonCreator
    public GoToFloorMessage(@JsonProperty("destinationFloor") int destinationFloor,@JsonProperty("elevatorId") int elevatorId) {
        // Opcode for this message is 2
        super(OpCodes.GO_TO_FLOOR.getOpCode());
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Go To Floor Message\n\tDestination Floor: " +
        destinationFloor + 
        "\n\t Elevator Id: " +
        elevatorId;
    }

    @Override
    public String summary(){
        return "GoToFloor: floor-"  + destinationFloor  + " elevator-" + elevatorId;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() { return elevatorId; }
}