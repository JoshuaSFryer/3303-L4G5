package com.sysc3303.communication;

import com.sysc3303.commons.Direction;

public class GUIElevatorMoveMessage extends Message {

    private int currentFloor;
    private Direction currentDirection;
    private boolean doorOpen;

    public GUIElevatorMoveMessage(byte opcode, int floor, Direction dir, boolean door) {
        super(opcode);
        //TODO: ElevatorVector
        this.currentFloor = floor;
        this.currentDirection = dir;
        this.doorOpen = door;
    }
}
