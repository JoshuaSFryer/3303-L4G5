package com.sysc3303.simulator;

import java.text.ParseException;

public class DoorStickEvent extends StickEvent{

    public DoorStickEvent(String[] eventStringArray) throws ParseException {
        super(eventStringArray);
    }

    // sends the door stick request to the elevator system
    public void send(){
        ElevatorSender.getInstance().sendDoorStick(elevatorId, numSecondsStuck);
    }

    @Override
    public String toString(){
        return "DoorStickEvent:\n\tTimestamp: " + timestamp + "\n\tElevatorId: " + elevatorId + "\n\tnumSecondsStuck: " + numSecondsStuck;
    }
}
