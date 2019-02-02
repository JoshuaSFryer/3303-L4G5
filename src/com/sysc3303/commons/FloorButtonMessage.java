package com.sysc3303.commons;

import java.util.Date;

public class FloorButtonMessage extends Message{
    private final int floor;
    private final Direction direction;
    private final Date time;

    public FloorButtonMessage(int floor, Direction direction, Date time) {
        super((byte)0);
        this.floor = floor;
        this.direction = direction;
        this.time = time;
    }

    public String toString(){
        return "Floor Button Request";
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