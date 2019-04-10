package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message indicated that an elevator is currently stuck
 *
 * @author Mattias Lightstone
 */
public class StuckMessage extends Message{
    private final int elevatorId;

    /**
     * Constructor
     * @param elevatorId the id of the elevator that is stuck
     */
    @JsonCreator
    public StuckMessage(@JsonProperty("elevatorId") int elevatorId) {
        super(OpCodes.STUCK.getOpCode());
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

