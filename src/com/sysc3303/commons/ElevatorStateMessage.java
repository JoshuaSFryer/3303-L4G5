package com.sysc3303.commons;

import com.sysc3303.elevator.ElevatorVector;

public class ElevatorStateMessage extends Message{
    private final ElevatorVector elevatorVector;
    private final int elevatorId;

    public ElevatorStateMessage(ElevatorVector elevatorVector, int elevatorId) {
        super((byte)4);
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
}