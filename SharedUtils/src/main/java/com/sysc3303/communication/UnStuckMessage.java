package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnStuckMessage extends Message{
    private final int elevatorId;

    @JsonCreator
    public UnStuckMessage(@JsonProperty("elevatorId") int elevatorId) {
        super(OpCodes.UNSTUCK.getOpCode());
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Stuck Message\n\tElevator Id: " +
                elevatorId;
    }

    @Override
    public String summary(){
        return "Stuck Message: elevatorId-"  + elevatorId;
    }

    public int getElevatorId() {
        return elevatorId;
    }

}


