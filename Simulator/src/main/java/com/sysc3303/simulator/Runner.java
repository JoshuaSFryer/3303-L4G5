package com.sysc3303.simulator;

import com.sysc3303.commons.ConfigListener;

public class Runner {
    public static void main(String args[]) throws InterruptedException{
        if(args.length > 1){
            if(args[1] .equals("config")){
                new ConfigListener().run();
            }
        }

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
