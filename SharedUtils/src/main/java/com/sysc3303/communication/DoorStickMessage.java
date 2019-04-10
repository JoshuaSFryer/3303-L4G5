package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message requesting that a specific elevator door be stuck for a specified amount of time.
 */
public class DoorStickMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    /**
     * Constructor
     * @param elevatorId the id of the elevator to be stuck
     * @param numSecondsStuck the number of seconds to stick the elevator door
     */
    @JsonCreator
    public DoorStickMessage(@JsonProperty("elevatorId") int elevatorId,@JsonProperty("numSecondsStuck") int numSecondsStuck) {
        super(OpCodes.DOOR_STICK.getOpCode());
        this.elevatorId = elevatorId;
        this.numSecondsStuck = numSecondsStuck;
    }

    @Override
    public String toString(){
        return "DoorStickRequest\n\tElevator Id: " +
                elevatorId +"\n\tNumber of Seconds to Stick: " + numSecondsStuck;
    }

    @Override
    public String summary(){
        return "DoorStickRequest: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }
}
