package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

/**
 * Message from Simulator to Floor to simulate
 * a floor button being clicked
 * Contains floor and the direction of the button clicked
 *
 * @author Mattias Lightstone
 */
public class FloorClickSimulationMessage extends Message{
    private int floor;
    private Direction direction;

    /**
     * Constructor
     * @param floor the floor of the button that should be clicked
     * @param direction the direction of the button that should be clicked
     */
    @JsonCreator
    public FloorClickSimulationMessage(@JsonProperty("floor") int floor,@JsonProperty("direction") Direction direction) {
        // Opcode for this message is 5
        super(OpCodes.FLOOR_CLICK_SIMULATION.getOpCode());
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public String toString(){
        return "Floor Click Simulation Message\n\tfloor: " +
                floor +"\n\tdirection: " + direction;
    }

    @Override
    public String summary(){
        return "FloorClickSim: floor-"  + floor  + " direction-" + direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }
}