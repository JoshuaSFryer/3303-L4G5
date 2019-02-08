package com.sysc3303.simulator;
import java.util.*;

/**
 * @author Mattias Lightstone
 *
 * Event timer assigns times to TimedEvents for them to run
 *
 */
public class EventTimer extends Timer{
    private static EventTimer instance;

    /**
     * Singleton pattern has a private empty constructor
     */
    private EventTimer(){
    }

    /**
     * There can only be one instance of Eventtimer at a time
     * Returns and instance of Eventtimer
     * If there is already an instance it returns it
     * If not it makes a new one
     *
     * @return an instance of EventTimer
     */
    public static EventTimer getInstance() {
        if (instance == null){
            instance = new EventTimer();
        }
        return instance;
    }

    public static synchronized void setNull(){ instance = null; }

    /**
     * Adds an Event to the event timer by making it a timed event and scheduling it to run at it's specified time
     * @param event the event that will mbe scheduled
     */
    public void add(Event event){
        TimedEvent timedEvent = new TimedEvent(event);
        // FIXME This is for testing purposes, I don't want to wait till the particular times
        this.schedule(timedEvent, 5000);
    }
}

