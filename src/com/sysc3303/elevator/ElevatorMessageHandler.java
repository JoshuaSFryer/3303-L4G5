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
            case 2:
                // TODO what happens when you receive GoToFloor
            	GoToFloorMessage castMessage = (GoToFloorMessage) message;
            	context.goToFloor(castMessage.getDestinationFloor());
                break;
            default:
            	// throw new BadMessageTypeException("This message cannot be handled by this module!");
            	System.out.println("This message type is not handled by this module!");
                
        }
    }

    public void sendElevatorState(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }
    public void sendElevatorButton(int destinationFloor, int elevatorId){
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date());
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }
}

