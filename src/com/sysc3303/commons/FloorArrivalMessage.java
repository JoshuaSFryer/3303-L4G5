package com.sysc3303.commons;

public class FloorArrivalMessage extends Message{
    private int floor;
    private Direction currentDirection;

    public FloorArrivalMessage(int floor, Direction currentDirection) {
        super((byte)1);
        this.floor = floor;
        this.currentDirection = currentDirection;
    }

    public String toString(){
        return "Floor Button Request";
    }

    public int getFloor() {
        return floor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}