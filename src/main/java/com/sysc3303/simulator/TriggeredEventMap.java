package com.sysc3303.simulator;
import com.sysc3303.commons.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Once a floor event has been sent from the simulator the event is moved to the TriggeredEventMap
 * It is a nested HashMap that indexes the event first by the floor, then by direction
 *
 * When an elevator arrives and the simulator receives a message from the floorsystem the floor and direction are
 * looked up in the hashmap to return the events that correspond to it and subsequently send elevator click messages
 *
 * This is a Singleton, there can only be one instance at a time
 *
 * TODO does this work if there are multiple events! NO
 */
public class TriggeredEventMap {
    public static TriggeredEventMap instance;
    // TODO this will need to be a list of events
    private HashMap<Integer, HashMap<Direction, List<Event>>> map;

    /**
     * Singleton patern uses a private empty constructor
     */
    private TriggeredEventMap(){}

    /**
     * Returns an instance of Triggered Event Map
     * If there already is one return it
     * If there isn't construct a new one and return it
     * @return instance of Triggered Event Map
     */
    public static TriggeredEventMap getInstance(){
        if (instance == null){
            instance = new TriggeredEventMap();
            instance.map = new HashMap<>();
        }
        return instance;
    }

    /**
     * Resets the instance of the singleton to null
     */
    public static synchronized void setNull(){ instance = null;}

    /**
     * Add an event to the hashmap
     * Indexes by direction and floor
     * @param event the event to be added
     * @return returns a boolean if it is added successfully
     */
    public boolean add(Event event){
        Direction direction = event.getDirection();
        Integer floor = new Integer(event.getFloor());
        HashMap<Direction, List<Event>> subMap;
        List<Event> eventList;

        // if the map doesn't yet have that floor mapped
        if (!map.containsKey(floor)){
            // make a new inner hashmap
            subMap = new HashMap<>();
            // add the floor to the map
            map.put(floor, subMap);
        }
        // if there is an index for the floor
        else {
            // get that index
            subMap = map.get(floor);
        }
        // if the submap does not yet contain an index for direction
        if (!subMap.containsKey(direction)){
            // make a new list for events
            eventList = new ArrayList<>();
            // add the event list to the inde
            subMap.put(direction, eventList);
        }
        // if there is an index for the direction
        else {
            // get the value at that index
            eventList = subMap.get(direction);
        }
        // add the event to the list in the map
        // return true if successful
        // false if unsuccessful
        return eventList.add(event);
    }

    /**
     * looks up the events in the hashmap by their floor and direction
     * sends those events to the elevatorId Elevator
     * @param floor the floor key
     * @param direction the direction key
     * @param elevatorId the elevator to send t
     */
    public void send(int floor, Direction direction, int elevatorId){
        List<Event> eventList = map.get(floor).remove(direction);
        for (Event event: eventList){
            ElevatorSender.getInstance().sendElevatorClick(elevatorId, event.getElevatorButton());
        }
    }

}
