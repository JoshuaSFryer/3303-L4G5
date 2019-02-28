package com.sysc3303.simulator;

import java.text.ParseException;

public class ElevatorStickEvent extends StickEvent{

    public ElevatorStickEvent(String[] eventStringArray) throws ParseException {
        super(eventStringArray);
    }

    public void send(){
        ElevatorSender.getInstance().sendElevatorStick(elevatorId, numSecondsStuck);
    }
}

