package com.sysc3303.simulator;

import java.text.ParseException;

public class ElevatorStickEvent extends StickEvent{

    public ElevatorStickEvent(String[] eventStringArray) throws ParseException {
        super(eventStringArray);
    }

    // Sends the elevator stick request to the elevator system
    public void send(){
        ElevatorSender.getInstance().sendElevatorStick(elevatorId, numSecondsStuck);
    }

    @Override
    public String toString(){
        return "ElevatorStickEvent:\n\tTimestamp: " + timestamp + "\n\tElevatorId: " + elevatorId + "\n\tnumSecondsStuck: " + numSecondsStuck;
    }
}

