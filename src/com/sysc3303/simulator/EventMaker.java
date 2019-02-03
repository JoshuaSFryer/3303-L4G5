package com.sysc3303.simulator;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class EventMaker {

    public EventMaker(){
    }

    public void addEventsFromFileToTimer(String filename){
        FileParser fp = FileParser.getInstance();
        try {
            fp.parse(filename);
        } catch (IOException e){
            System.out.println("Could not find file: " + filename);
            return;
        } catch (ParseException e){
            System.out.println("could not parse file: " + filename);
            return;
        }
        List<String[]> parsedEvents = fp.getParsed();
        for (String[] element: parsedEvents){
            createEventAndAddToTimer(element);
        }
    }

    private boolean createEventAndAddToTimer(String[] stringArray){
        Event event;
        try {
            event = new Event(stringArray);
        } catch (ParseException e){
            System.out.println("Could not parse: " + stringArray);
            return false;
        }
        EventTimer eventTimer = EventTimer.getInstance();
        return eventTimer.add(event);
    }
}
