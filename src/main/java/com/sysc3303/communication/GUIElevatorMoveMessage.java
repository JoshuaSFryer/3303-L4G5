package com.sysc3303.communication;

import com.sysc3303.commons.Direction;

/**
 * Message sent when an elevator is moving.
 */
public class GUIElevatorMoveMessage extends Message {

    public final int currentFloor;
    public final Direction currentDirection;
    public final boolean doorOpen;
    public final int ID;

    public GUIElevatorMoveMessage(byte opcode, int elevatorID, int floor,
                                  Direction dir, boolean door) {
        super(opcode);
        this.currentFloor = floor;
        this.currentDirection = dir;
        this.doorOpen = door;
        this.ID = elevatorID;
    }

    @Override
    public String toString() {
        return "Elevator " + this.ID + "\nCurrent floor: " + this.currentFloor +
                " Going: " + this.currentDirection.name() + "Door is: " +
                this.doorOpen;
    }
}
