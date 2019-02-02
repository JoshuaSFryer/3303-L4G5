package com.sysc3303.elevator;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ElevatorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    final int schedulerPort = 7002;
    private InetAddress schedulerAddress;


    public ElevatorMessageHandler(int receivePort){
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
            case OpCodes.FLOOR_BUTTON.getOpcode():
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive ElevatorButton
                break;
            case 5:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    // TODO Rename this if you would like to
    public void sendElevatorState(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }
    public void sendElevatorButton(int destinationFloor, int elevatorId){
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date());
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }
}
