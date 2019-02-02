package com.sysc3303.commons;

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

    public FloorButtonMessage(int floor, Direction direction, Date time) {
        // Opcode for this message is 0
        super((byte)0);
        this.floor = floor;
        this.direction = direction;
        this.time = time;
    }

    public String toString(){
        return "Floor Button Message\n\tfloor: " +
                floor +"\n\tdirection: " + direction +
                "\n\ttime: " + time;
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
}