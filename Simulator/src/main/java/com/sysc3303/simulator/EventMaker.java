package com.sysc3303.simulator;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author Mattias Lightstone
 *
 * Reads the input file, parses the input and creates events for each line of the input file
 * Then it adds the event to the Timer
 */
public class EventMaker {

    // TODO Definitely could be a singleton
    public EventMaker(){
    }

    /**
     * Reads the input file, parses the input and creates events for each line of the input file
     * Then it adds the event to the Timer
     * @param filePath the filepath to the input file
     */
    public void addEventsFromFileToTimer(String filePath){
        FileParser fp = FileParser.getInstance();
        try {
            fp.parse(filePath);
        } catch (IOException e){
            System.out.println("Could not find file: " + filePath);
            return;
        } catch (ParseException e){
            System.out.println("could not parse file: " + filePath);
            return;
        }
        List<String[]> parsedEvents = fp.getParsed();
        for (String[] element: parsedEvents){
            createEventAndAddToTimer(element);
        }
    }

    /**
     * creates an event from a string array
     * used to create an event from a line in the input file
     *
     * @param stringArray the line in the file containing event info
     * @return True if the event is properly created, else false
     */
    private void createEventAndAddToTimer(String[] stringArray){
        Event event;
        try {
            event = new Event(stringArray);
        } catch (ParseException e){
            System.out.println("Could not parse: " + stringArray);
            return;
        }
        EventTimer eventTimer = EventTimer.getInstance();
        eventTimer.add(event);
    }
}
