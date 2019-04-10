package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

import java.util.Date;

/**
 * Message from Floor System to the Scheduler
 * Indicates that a button has been pressed
 *
 * Contains the floor and direction of the button pressed as well as the time pressed
 *
 * @author Mattias Lightstone
 */

public class FloorButtonMessage extends Message{
    private final int floor;
    private final Direction direction;
    private final Date time;
    private final long pressedTime;

    /**
     * Constructor
     * @param floor the floor on which the button was pressed
     * @param direction the direction of the button that was pressed
     * @param time the time that message was sent
     * @param pressedTime the time that the button was pressed
     */
    @JsonCreator
    public FloorButtonMessage(@JsonProperty("floor") int floor,@JsonProperty("direction") Direction direction,@JsonProperty("time") Date time, long pressedTime) {
        // Opcode for this message is 0
        super(OpCodes.FLOOR_BUTTON.getOpCode());
        this.floor = floor;
        this.direction = direction;
        this.time = time;
        this.pressedTime = pressedTime;
    }

    @Override
    public String toString(){
        return "Floor Button Message\n\tfloor: " +
                floor +"\n\tdirection: " + direction +
                "\n\ttime: " + time;
    }

    @Override
    public String summary(){
        return "FloorButton: floor-"  + floor  + " direction-" + direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public Date getTime() {
        return time;
    }
    
    public long getPressedTime() {
    	return pressedTime;
    }
}