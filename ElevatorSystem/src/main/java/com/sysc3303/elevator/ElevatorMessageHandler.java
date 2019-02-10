package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ElevatorVector;
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
    private static ElevatorMessageHandler instance;

    private InetAddress schedulerAddress;
    private ElevatorSystem context;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));

    public static ElevatorMessageHandler getInstance(int receivePort, ElevatorSystem context){
        if (instance == null){
            instance = new ElevatorMessageHandler(receivePort, context);
        }
        return instance;
    }

    public ElevatorMessageHandler(int receivePort, ElevatorSystem context){
        super(receivePort);
        this.context = context;
        try{
            schedulerAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public void received(Message message){
        switch (message.getOpcode()){
            case 2:
            	GoToFloorMessage goToFloorMessage = (GoToFloorMessage) message;
            	// get elevator based on the id in the message
            	Elevator elevator = context.getElevators().get(goToFloorMessage.getElevatorId());
            	// send it to the floor in the message
            	elevator.goToFloor(goToFloorMessage.getDestinationFloor());
                break;
            case 6:
                ElevatorClickSimulationMessage elevatorClickSimulationMessage = (ElevatorClickSimulationMessage) message;
                // get elevator based on the id in the message
                Elevator elevator1 = context.getElevators().get(elevatorClickSimulationMessage.getElevatorId());
                // send press the button in that elevator
                elevator1.pressButton(elevatorClickSimulationMessage.getFloor());
                break;
            default:
            	// throw new BadMessageTypeException("This message cannot be handled by this module!");
            	System.out.println("This message type is not handled by this module!");
            	break;
                
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

