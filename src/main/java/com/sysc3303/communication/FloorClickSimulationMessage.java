package com.sysc3303.communication;

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

    public FloorClickSimulationMessage(int floor, Direction direction) {
        // Opcode for this message is 5
        super(OpCodes.FLOOR_CLICK_SIMULATION.getOpCode());
        this.floor = floor;
        this.direction = direction;
    }

    public String toString(){
        return "Floor Click Simulation Message\n\tfloor: " +
                floor +"\n\tdirection: " + direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }
}