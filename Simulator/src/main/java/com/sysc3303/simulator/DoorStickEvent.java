package com.sysc3303.simulator;

import java.text.ParseException;

public class DoorStickEvent extends StickEvent{

    public DoorStickEvent(String[] eventStringArray) throws ParseException {
        super(eventStringArray);
    }

    public void send(){
        ElevatorSender.getInstance().sendDoorStick(elevatorId, numSecondsStuck);
    }
}
