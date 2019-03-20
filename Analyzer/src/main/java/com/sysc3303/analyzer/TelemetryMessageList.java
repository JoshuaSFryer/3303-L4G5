package com.sysc3303.analyzer;

import java.util.ArrayList;
import java.util.List;

public class TelemetryMessageList {
    public List<Long> elevatorButtonTimeList;
    public List<Long> floorButtonTimeList;
    public List<Long> arrivalTimeList;

    public TelemetryMessageList(){
        arrivalTimeList = new ArrayList<>();
        floorButtonTimeList = new ArrayList<>();
        elevatorButtonTimeList = new ArrayList<>();
    }

    public void addArrivalTime(long time){
        System.out.println(time + " added to arrival time list");
        arrivalTimeList.add(time);
    }

    public void addFloorButtonTime(long time){
        System.out.println(time + " added to floor button list");
        floorButtonTimeList.add(time);
    }

    public void addElevatorButtonTime(long time){
        System.out.println(time + " added to elevator button list");
        elevatorButtonTimeList.add(time);
    }

}
