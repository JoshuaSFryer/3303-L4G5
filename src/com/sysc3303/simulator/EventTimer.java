package com.sysc3303.simulator;
import java.util.*;

public class EventTimer extends Timer{
    private static EventTimer instance;

    private EventTimer(){
    }

    public static EventTimer getInstance() {
        if (instance == null){
            instance = new EventTimer();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean add(Event event){
        TimedEvent timedEvent = new TimedEvent(event);
        this.schedule(timedEvent, 5000);
        return true;
    }
}

