package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message from Simulator to Elevator to simulate
 * an elevator button being clicked
 * Contains selected floor and the id of the elevator in which the button was pressed
 *
 * @author Mattias Lightstone
 */
public class ElevatorClickSimulationMessage extends Message{
    private int floor;
    private int elevatorId;

    @JsonCreator
    public ElevatorClickSimulationMessage(@JsonProperty("floor") int floor, @JsonProperty("elevatorId") int elevatorId) {
        // Opcode for this message is 6
        super(OpCodes.ELEVATOR_CLICK_SIMULATION.getOpCode());
        this.floor = floor;
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Elevator Button Click Simulation Message\n\televatorId: " +
                elevatorId +"\n\tfloor: " + floor;
    }

    @Override
    public String summary(){
        return "ElevatorClickSim: floor-"  + floor  + " elevatorId-" + elevatorId;
    }

    public int getFloor() {
        return floor;
    }

    public int getElevatorId() {
        return elevatorId;
    }
}