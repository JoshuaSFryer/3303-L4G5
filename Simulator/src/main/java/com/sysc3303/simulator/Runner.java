package com.sysc3303.simulator;

public class Runner {
    public static void main(String args[]) throws InterruptedException{
        EventMaker eventMaker = new EventMaker();
        if (args.length < 1){
            System.out.println("No file was provided");
            return;
        }
        eventMaker.addEventsFromFileToTimer(args[0]);
        System.out.println(System.getProperty("user.dir"));
        while(true){
        }
    }
}
