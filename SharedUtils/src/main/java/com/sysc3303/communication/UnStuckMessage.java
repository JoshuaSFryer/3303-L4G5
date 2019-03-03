package com.sysc3303.communication;

public class UnStuckMessage extends Message{
    private final int elevatorId;

    public UnStuckMessage(int elevatorId) {
        super(OpCodes.UNSTUCK.getOpCode());
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Stuck Message\n\tElevator Id: " +
                elevatorId;
    }

    @Override
    public String getSummary(){
        return "Stuck Message: elevatorId-"  + elevatorId;
    }

    public int getElevatorId() {
        return elevatorId;
    }

}


