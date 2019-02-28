package com.sysc3303.simulator;

import java.text.ParseException;

public abstract class StickEvent extends Event{

    protected int elevatorId;
    protected int numSecondsStuck;

    public StickEvent(String[] eventStringArray) throws ParseException {
        super(eventStringArray);
        elevatorId = Integer.parseInt(eventStringArray[2]);
        numSecondsStuck = Integer.parseInt(eventStringArray[3]);
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }

    public void setNumSecondsStuck(int numSecondsStuck) {
        this.numSecondsStuck = numSecondsStuck;
    }
}
