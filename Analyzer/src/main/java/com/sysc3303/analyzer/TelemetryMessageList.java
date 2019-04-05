package com.sysc3303.analyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores telemetry data after being recieved
 * and allows for processing
 */
public class TelemetryMessageList {
    public List<Long> elevatorButtonTimeList;
    public List<Long> floorButtonTimeList;
    public List<Long> arrivalTimeList;
    public TelemetryMath math;

    /**
     * Constructor creates three lists and instantiates a math object to process the data
     */
    public TelemetryMessageList(){
        arrivalTimeList = new ArrayList<>();
        floorButtonTimeList = new ArrayList<>();
        elevatorButtonTimeList = new ArrayList<>();
        math = TelemetryMath.getInstance();
    }

    /**
     * adds a time to the arrival time list and prints the current mean and variance
     * @param time the time to be added
     */
    public void addArrivalTime(long time){
        System.out.println(time + " added to arrival time list");
        arrivalTimeList.add(time);
        printAnalysis("Arrival response", arrivalTimeList);
    }

    /**
     * adds a time to the floor button time list and prints the current mean and variance
     * @param time the time to be added
     */
    public void addFloorButtonTime(long time){
        System.out.println(time + " added to floor button list");
        floorButtonTimeList.add(time);
        printAnalysis("Floor Button Response", floorButtonTimeList);
    }

    /**
     * adds a time to the elevator button time list and prints the current mean and variance
     * @param time the time to be added
     */
    public void addElevatorButtonTime(long time){
        System.out.println(time + " added to elevator button list");
        elevatorButtonTimeList.add(time);
        printAnalysis("Elevator Button Response", elevatorButtonTimeList);
    }


    /**
     * Helper method to print the mean and variance of a list
     * @param name The name to be printed beside the analysis
     * @param list the list for the analysis to be calculated
     */
    private void printAnalysis(String name, List<Long> list){
        long mean = (long) math.getMean(list);
        long var = (long) math.getVariance(list, mean);
        System.out.println(name + ":");
        System.out.println("\tMean: " + mean + "ms");
        System.out.println("\tVariance: " + var + "ms");
    }

}
