package com.sysc3303.communication;

public class ElevatorStickMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    public ElevatorStickMessage(int elevatorId, int numSecondsStuck) {
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
    public String getSummary(){
        return "ElevatorStickRequest: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }

}
