package com.sysc3303.floor;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class FloorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    final int schedulerPort = 7002;
    private InetAddress schedulerAddress;


    public FloorMessageHandler(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            schedulerAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // Shouldn't have this on the scheduler
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorButton
                break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    // TODO Rename this if you would like to
    public void sendFloorButton(int floor, Direction direction){
        FloorButtonMessage floorButtonMessage = new FloorButtonMessage(floor, direction, new Date());
        send(floorButtonMessage, schedulerAddress, schedulerPort);
    }
}
