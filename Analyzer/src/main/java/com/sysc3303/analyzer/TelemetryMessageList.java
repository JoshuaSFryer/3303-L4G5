package com.sysc3303.analyzer;

import java.util.ArrayList;
import java.util.List;

public class TelemetryMessageList {
    public List<Long> elevatorButtonTimeList;
    public List<Long> floorButtonTimeList;
    public List<Long> arrivalTimeList;
    public TelemetryMath math;

    public TelemetryMessageList(){
        arrivalTimeList = new ArrayList<>();
        floorButtonTimeList = new ArrayList<>();
        elevatorButtonTimeList = new ArrayList<>();
        math = TelemetryMath.getInstance();
    }

    public void addArrivalTime(long time){
        System.out.println(time + " added to arrival time list");
        arrivalTimeList.add(time);
        long avg = math.getMean(arrivalTimeList);
        long var = math.getVariance(arrivalTimeList, avg);
        printAnalysis("Arrival response", arrivalTimeList);
    }

    public void addFloorButtonTime(long time){
        System.out.println(time + " added to floor button list");
        floorButtonTimeList.add(time);
        printAnalysis("Floor Button Response", floorButtonTimeList);
    }

    public void addElevatorButtonTime(long time){
        System.out.println(time + " added to elevator button list");
        elevatorButtonTimeList.add(time);
        printAnalysis("Elevator Button Response", elevatorButtonTimeList);
    }

    private void printAnalysis(String name, List<Long> list){
        long avg = math.getMean(list);
        long var = math.getVariance(list, avg);
        System.out.println(name + ":");
        System.out.println("\tMean: " + avg + "ns");
        System.out.println("\tVariance: " + var + "ns");
    }

}
