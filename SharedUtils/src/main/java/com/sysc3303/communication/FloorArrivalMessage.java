package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

/**
 * Message from Scheduler to Floor indicating that an
 * elevator has arrived at a requested floor
 * Contains floor and the direction that the elevator will travel
 *
 * @author Mattias Lightstone
 */
public class FloorArrivalMessage extends Message{
    private int floor;
    private Direction currentDirection;
    private int elevatorId;

    /**
     * Constructor
     * @param floor the floor at which the elevator has arrived
     * @param currentDirection the direction that the elevator will travel after arrival
     * @param elevatorId the id of the elevator which has arrived
     */
    @JsonCreator
    public FloorArrivalMessage(@JsonProperty("floor") int floor, @JsonProperty("currentDirection") Direction currentDirection, @JsonProperty("elevatorId") int elevatorId) {
        // Opcode for this message is 1
        super(OpCodes.FLOOR_ARRIVAL.getOpCode());
        this.floor = floor;
        this.currentDirection = currentDirection;
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Floor Arrival Message\n\tfloor: " +
                floor +"\n\tcurrent direction: " + currentDirection;
    }

    @Override
    public String summary(){
        return "FloorArrival: floor-"  + floor  + " direction-" + currentDirection + " elevator-" + elevatorId;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getElevatorId() { return elevatorId; }
}