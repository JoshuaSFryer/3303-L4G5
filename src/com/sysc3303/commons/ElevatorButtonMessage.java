package com.sysc3303.commons;

import java.util.Date;

public class ElevatorButtonMessage extends Message{
    private final int destinationFloor;
    private final int elevatorId;
    private final Date time;

    public ElevatorButtonMessage(int destinationFloor, int elevatorId, Date time) {
        super((byte) 5);
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
        this.time = time;
    }

    public String toString(){
        return "Elevator Button Request\n\tDesination Floor: " +
                destinationFloor +"\n\tElevator Id: " + elevatorId +
                "\n\tTime: " + time;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public Date getTime() {
        return time;
    }
}