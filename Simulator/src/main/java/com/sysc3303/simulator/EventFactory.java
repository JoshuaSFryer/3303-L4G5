package com.sysc3303.simulator;

import java.text.ParseException;

public class EventFactory {

    public Event makeEvent(String[] eventStringArray) throws ParseException {
        Event event = null;
        // the type of event is specified as the second string in the event file
        switch(eventStringArray[1]){
            case "request":
                event = new RequestEvent(eventStringArray);
                break;
            case "stick_door":
                // make door stick error event
                event = new DoorStickEvent(eventStringArray);
                break;
            case "stick_elevator":
                // make elevator stick
                event = new ElevatorStickEvent(eventStringArray);
                break;
            default:
                // if neither, print type does not exist
                System.out.println("Event type : " + eventStringArray[1] + " does not exist");
                break;
        }
    }
}
