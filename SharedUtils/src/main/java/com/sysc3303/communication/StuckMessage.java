package com.sysc3303.communication;

public class StuckMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    public StuckMessage(int elevatorId, int numSecondsStuck) {
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
    public String getSummary(){
        return "Stuck Message: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }

}

