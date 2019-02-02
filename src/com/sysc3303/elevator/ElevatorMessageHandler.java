package com.sysc3303.elevator;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;


/*
@SuppressWarnings("serial")
class BadMessageTypeException extends Exception {
	BadMessageTypeException(String msg) {
		super(msg);
	}
}
*/

public class ElevatorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    final int schedulerPort = 7002;
    private InetAddress schedulerAddress;
    private Elevator context;


    public ElevatorMessageHandler(int receivePort, Elevator context){
        super(receivePort);
        this.context = context;
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
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
            	GoToFloorMessage castMessage = (GoToFloorMessage) message;
            	context.goToFloor(castMessage.getDestinationFloor());
                break;
            case 5:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                break;
            default:
            	// throw new BadMessageTypeException("This message cannot be handled by this module!");
            	System.out.println("This message type is not handled by this module!");
                
        }
    }

    public void sendElevatorState(ElevatorVector elevatorVector, int elevatorId){
    	System.out.println("Arrived at a floor, notifying scheduler");
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }
    public void sendElevatorButton(int destinationFloor, int elevatorId){
    	System.out.println("Elevator button pressed, notifying scheduler");
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date());
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }
}

