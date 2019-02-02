package com.sysc3303.commons;

import com.sysc3303.elevator.ElevatorVector;

public class ElevatorStateMessage extends Message{
    private final ElevatorVector elevatorVector;

    public ElevatorStateMessage(ElevatorVector elevatorVector) {
        super((byte)4);
        this.elevatorVector = elevatorVector;
    }

    public String toString(){
        return "Go To Floor Request";
    }

    public ElevatorVector elevatorVector() {
        return elevatorVector();
    }
}