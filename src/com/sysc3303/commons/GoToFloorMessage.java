package com.sysc3303.commons;

public class GoToFloorMessage extends Message{
    private final int destinationFloor;

    public GoToFloorMessage(int destinationFloor) {
        super((byte) 2);
        this.destinationFloor = destinationFloor;
    }

    public String toString(){
        return "Go To Floor Request";
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}