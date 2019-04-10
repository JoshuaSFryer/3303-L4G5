package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.ElevatorVector;

/**
 * Message sent from Elevator to Scheduler to update
 * it about what floor it's on, and the direction of travel
 *
 * @author Mattias Lightstone
 */
public class ElevatorStateMessage extends Message{
    private final ElevatorVector elevatorVector;
    private final int elevatorId;

    /**
     * Constructor
     * @param elevatorVector the current status of the elevator, including direction and floor
     * @param elevatorId the id of the elevator
     */
    @JsonCreator
    public ElevatorStateMessage(@JsonProperty("elevatorVector") ElevatorVector elevatorVector,@JsonProperty("elevatorId") int elevatorId) {
        // Opcode for this message is 3
        super(OpCodes.ELEVATOR_STATE.getOpCode());
        this.elevatorVector = elevatorVector;
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Elevator State Message\n\tElevator Vector: " +
                elevatorVector +"\n\tElevator Id: " + elevatorId;

    }

    @Override
    public String summary(){
        return "ElevatorState: currentFloor-"  + elevatorVector.currentFloor  + " direction-" + elevatorVector.currentDirection + " elevator-" + elevatorId;
    }

    public ElevatorVector getElevatorVector() {
        return elevatorVector;
    }
    public int getElevatorId() { return elevatorId; }
}