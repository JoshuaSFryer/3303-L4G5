package com.sysc3303.communication;

import com.sysc3303.commons.Direction;

public class GUIElevatorMoveMessage extends Message {

    public final int currentFloor;
    public final Direction currentDirection;
    public final boolean doorOpen;

    public GUIElevatorMoveMessage(byte opcode, int floor, Direction dir, boolean door) {
        super(opcode);
        //TODO: ElevatorVector
        this.currentFloor = floor;
        this.currentDirection = dir;
        this.doorOpen = door;
    }

    @Override
    public String toString() {
        return "Current floor: " + this.currentFloor + " Going: " + this.currentDirection.name() + "\nDoor is: " +
                this.doorOpen;
    }
}
