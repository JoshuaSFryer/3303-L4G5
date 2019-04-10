package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.OpCodes;

import static com.sysc3303.communication.OpCodes.ELEVATOR_UPDATE_GUI;

/**
 * Message sent when an elevator is moving.
 */
public class GUIElevatorMoveMessage extends Message {

    public final int currentFloor;
    public final Direction currentDirection;
    public final boolean doorOpen;
    public final int ID;
    public final int targetFloor;

    /**
     * Constructor
     * @param elevatorID The id of the elevator that is moving
     * @param floor the floor that the elevator is currently
     * @param dir the direction that the elevator is currently moving
     * @param door the status of the door false = closed, true = open
     */
    @JsonCreator
    public GUIElevatorMoveMessage(@JsonProperty("elevatorID") int elevatorID, @JsonProperty("floor") int floor,
                                  @JsonProperty("dir") Direction dir, @JsonProperty("door") boolean door, @JsonProperty("targetFloor") int targetFloor) {
        super(ELEVATOR_UPDATE_GUI.getOpCode());
        this.currentFloor = floor;
        this.currentDirection = dir;
        this.doorOpen = door;
        this.ID = elevatorID;
        this.targetFloor = targetFloor;
    }

    @Override
    public String toString() {
        String openClosed;
        if(this.doorOpen) {
            openClosed = "Open";
        } else {
            openClosed = "Closed";
        }

        return "Elevator " + this.ID + ": Current floor: " + this.currentFloor
                + " Going: " + this.currentDirection.name() + " Door is: "
                + openClosed + ", moving towards floor " + this.targetFloor;
    }
}
