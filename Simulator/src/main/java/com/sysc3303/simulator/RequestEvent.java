package com.sysc3303.simulator;
import com.sysc3303.commons.Direction;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class RequestEvent extends Event{
    private int floor, elevatorButton;
    private Direction direction;

    /**
     * Constructor that takes the parameters as one array of strings that need to be parsed
     * @param stringArray
     * @throws ParseException
     */
    public RequestEvent(String[] stringArray) throws ParseException{

        super(Integer.parseInt(stringArray[0]), stringArray[1]);


        this.floor = Integer.parseInt(stringArray[2]);
        this.direction = Direction.valueOf(stringArray[3].toUpperCase());
        this.elevatorButton = Integer.parseInt(stringArray[4]);
    }

    public String toString(){
        return "RequestEvent:\n\tTimestamp: " + timestamp + "\n\tFloor: " + floor + "\n\tDirection: " + direction + "\n\tElevator Button: " + elevatorButton;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getElevatorButton() {
        return elevatorButton;
    }

    public void setElevatorButton(int elevatorButton) {
        this.elevatorButton = elevatorButton;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void send(){
        FloorSender.getInstance().sendFloorClick(floor, getDirection());
        // add it to the triggered event map to send on receipt of floor arrival
        TriggeredEventMap.getInstance().add(this);
    }
}
