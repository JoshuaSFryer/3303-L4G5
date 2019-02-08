package com.sysc3303.communication;

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

    public ElevatorClickSimulationMessage(int floor, int elevatorId) {
        // Opcode for this message is 6
        super(OpCodes.ELEVATOR_CLICK_SIMULATION.getOpCode());
        this.floor = floor;
        this.elevatorId = elevatorId;
    }

    public String toString(){
        return "Elevator Button Click Simulation Message\n\televatorId: " +
                elevatorId +"\n\tfloor: " + floor;
    }

    public int getFloor() {
        return floor;
    }

    public int getElevatorId() {
        return elevatorId;
    }
}