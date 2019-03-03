package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StuckMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    @JsonCreator
    public StuckMessage(@JsonProperty("elevatorId") int elevatorId,@JsonProperty("numSecondsStuck") int numSecondsStuck) {
        super(OpCodes.STUCK.getOpCode());
        this.elevatorId = elevatorId;
        this.numSecondsStuck = numSecondsStuck;
    }

    @Override
    public String toString(){
        return "Stuck Message\n\tElevator Id: " +
                elevatorId +"\n\tNumber of Seconds to Stick: " + numSecondsStuck;
    }

    @Override
    public String summary(){
        return "Stuck Message: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }

}

