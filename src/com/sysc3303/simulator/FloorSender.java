package com.sysc3303.simulator;
import com.sun.media.sound.WaveFloatFileReader;
import com.sysc3303.commons.Direction;

public class FloorSender {
    private static FloorSender instance;

    public static FloorSender getInstance() {
        if (instance == null){
            instance = new FloorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean sendFloorClick(int floor, Direction direction){
        System.out.println("sendFloorClick has not been implemented yet");
        return true;
    }

    private FloorSender(){
    }
}
