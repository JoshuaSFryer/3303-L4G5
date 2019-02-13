package com.sysc3303.communication;

import com.sysc3303.elevator.ElevatorVector;

/**
 * Message sent from Elevator to Scheduler to update
 * it about what floor it's on, and the direction of travel
 *
 * @author Mattias Lightstone
 */
public class ElevatorStateMessage extends Message{
    private final ElevatorVector elevatorVector;
    private final int elevatorId;

    public ElevatorStateMessage(ElevatorVector elevatorVector, int elevatorId) {
        // Opcode for this message is 4
        super(OpCodes.ELEVATOR_STATE.getOpCode());
        this.elevatorVector = elevatorVector;
        this.elevatorId = elevatorId;
    }

    public String toString(){
        return "Elevator State Message\n\tElevator Vector: " +
                elevatorVector +"\n\tElevator Id: " + elevatorId;

    }

    public ElevatorVector getElevatorVector() {
        return elevatorVector;
    }
    public int getElevatorId() { return elevatorId; }
}