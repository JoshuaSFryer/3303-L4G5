package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.*;
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

public class ElevatorMessageHandler extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    private static ElevatorMessageHandler instance;

    private InetAddress schedulerAddress;
    private Elevator context;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));

    public static ElevatorMessageHandler getInstance(int receivePort, Elevator context){
        if (instance == null){
            instance = new ElevatorMessageHandler(receivePort, context);
        }
        return instance;
    }

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
            	GoToFloorMessage castMessage = (GoToFloorMessage) message;
            	context.goToFloor(castMessage.getDestinationFloor());
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

