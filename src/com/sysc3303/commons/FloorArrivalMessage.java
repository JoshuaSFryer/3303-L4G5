package com.sysc3303.commons;

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

    public FloorArrivalMessage(int floor, Direction currentDirection) {
        // Opcode for this message is 1
        super((byte)1);
        this.floor = floor;
        this.currentDirection = currentDirection;
    }

    public String toString(){
        return "Floor Arrival Message\n\tfloor: " +
                floor +"\n\tcurrent direction: " + currentDirection;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}