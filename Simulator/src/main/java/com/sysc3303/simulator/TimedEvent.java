package com.sysc3303.simulator;
import java.util.TimerTask;

/**
 * An event that is triggered at a specific time.
 * When the time comes, the simulator sends the event to FloorSystem
 *
 * @author Mattias Lightstone
 */

public class TimedEvent extends TimerTask {
    private Event event;

    public TimedEvent(Event event){
        this.event = event;
    }

    /**
     * run is called when the time associated with the event is called
     * the event is sent, then it is added to the TriggeredEventMap
     */
    @Override
    public void run(){
        System.out.println("\nThe event being sent is:\n" + event);
        // send it using the floor sender
        event.send();
    }
}
