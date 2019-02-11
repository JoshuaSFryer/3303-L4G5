package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
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
    private InetAddress uiAddress;
    private Elevator context;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    static int uiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));

    public static ElevatorMessageHandler getInstance(int receivePort, Elevator context){
        if (instance == null){
            System.out.println("ElevatorHandler: Binding port" + receivePort);
            instance = new ElevatorMessageHandler(receivePort, context);
        }
        return instance;
    }

    private ElevatorMessageHandler(int receivePort, Elevator context){
        super(receivePort);
        this.context = context;
        try {
            // Everything on localhost for now.
            schedulerAddress = InetAddress.getLocalHost();
            uiAddress = InetAddress.getLocalHost();
        } catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    @Override
    public void received(Message message){
        switch (message.getOpcode()){
            case 2:
            	GoToFloorMessage castMessage = (GoToFloorMessage) message;
            	context.goToFloor(castMessage.getDestinationFloor());
                break;
            case 6:
                ElevatorClickSimulationMessage elevatorClickSimulationMessage = (ElevatorClickSimulationMessage) message;
                // TODO this ony has one elevator and always sends the message to it
                context.pressButton(elevatorClickSimulationMessage.getFloor());
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
    public void updateUI(int elevatorID, int currentFloor, Direction dir, boolean open) {
        GUIElevatorMoveMessage msg = new GUIElevatorMoveMessage(elevatorID, currentFloor, dir, open);
        send(msg, uiAddress, uiPort);
    }
}

