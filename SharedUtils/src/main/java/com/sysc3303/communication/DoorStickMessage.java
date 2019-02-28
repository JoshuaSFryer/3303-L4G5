package com.sysc3303.communication;

public class DoorStickMessage extends Message{
    private final int elevatorId;
    private final int numSecondsStuck;

    public DoorStickMessage(int elevatorId, int numSecondsStuck) {
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
    public String getSummary(){
        return "DoorStickRequest: elevatorId-"  + elevatorId  + " seconds stuck-" + numSecondsStuck;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getNumSecondsStuck() {
        return numSecondsStuck;
    }


}
