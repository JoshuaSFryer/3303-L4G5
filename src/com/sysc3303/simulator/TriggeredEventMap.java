package com.sysc3303.simulator;
import com.sysc3303.commons.Direction;

import java.util.HashMap;

public class TriggeredEventMap {
    public static TriggeredEventMap instance;
    private HashMap<Integer, HashMap<Direction, Event>> map;

    private TriggeredEventMap(){}

    public static TriggeredEventMap getInstance(){
        if (instance == null){
            instance = new TriggeredEventMap();
            instance.map = new HashMap<>();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean add(Event event){
        Direction direction = event.getDirection();
        Integer floor = new Integer(event.getFloor());
        HashMap<Direction, Event> subMap;
        if (!map.containsKey(floor)){
            subMap = new HashMap<>();
            map.put(floor, subMap);
        }
        else {
            subMap = map.get(floor);
        }
        if (subMap.put(direction, event) == null){
            return false;
        }
        return true;
    }

    public boolean send(int floor, Direction direction, int elevatorNum){
        Event event = map.get(floor).remove(direction);
        return ElevatorSender.getInstance().sendElevatorClick(elevatorNum, event.getElevatorButton());
    }

}
