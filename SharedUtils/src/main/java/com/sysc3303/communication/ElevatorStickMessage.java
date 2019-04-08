package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A request message for a specified elevator to be stuck for a specified amount of time.
 *
 * @author Mattias Lightstone
 */
public class ElevatorStickMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    /**
     * Constructor
     * @param elevatorId the id of the elevator to be stuck
     * @param numSecondsStuck the number of seconds for it to stick
     */
    @JsonCreator
    public ElevatorStickMessage(@JsonProperty("elevatorId") int elevatorId,@JsonProperty("numSecondsStuck") int numSecondsStuck) {
        super(OpCodes.ELEVATOR_STICK.getOpCode());
        this.elevatorId = elevatorId;
        this.numSecondsStuck = numSecondsStuck;
    }

    @Override
    public String toString(){
        return "ElevatorStickRequest\n\tElevator Id: " +
                elevatorId +"\n\tNumber of Seconds to Stick: " + numSecondsStuck;
    }

    @Override
    public String summary(){
        return "ElevatorStickRequest: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }

}
