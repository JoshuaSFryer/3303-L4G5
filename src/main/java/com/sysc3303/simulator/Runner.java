package com.sysc3303.simulator;

public class Runner {
    public static void main(String args[]) throws InterruptedException{
        EventMaker eventMaker = new EventMaker();
        System.out.println(System.getProperty("user.dir"));
        eventMaker.addEventsFromFileToTimer("src/test/java/com/sysc3303/simulator/testEvents.txt");
        while(true){
        }
    }
}
