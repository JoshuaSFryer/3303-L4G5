package com.sysc3303.scheduler;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SchedulerMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with floor and elevator
    final int elevatorPort = 7000;
    final int floorPort = 7001;
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;

    public SchedulerMessageHandler(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            elevatorAddress = floorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // Shouldn't have this on the scheduler
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // Shouldn't have this on the scheduler
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // TODO what happens when you receive ElevatorButton
                break;
            case 5:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    // TODO Rename this if you would like to
    public void sendGoToFloor(int floor){
        GoToFloorMessage goToFloorMessage = new GoToFloorMessage(floor);
        send(goToFloorMessage, elevatorAddress, elevatorPort);
    }

    // TODO Rename this if you would like to
    public void sendFloorArival(int floor, Direction direction){
        FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(floor, direction);
        send(floorArrivalMessage, floorAddress, floorPort);
    }
}
