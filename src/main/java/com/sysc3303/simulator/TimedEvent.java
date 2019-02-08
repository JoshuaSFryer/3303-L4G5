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
        FloorSender.getInstance().sendFloorClick(event.getFloor(), event.getDirection());
        // add it to the triggered event map to send on receipt of floor arrival
        TriggeredEventMap.getInstance().add(event);
    }
}
