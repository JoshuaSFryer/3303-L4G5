package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.*;
import com.sysc3303.scheduler.SchedulerMessageHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;


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
    private Logger         log = Logger.getLogger(ElevatorMessageHandler.class);
    
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
            	log.info("Recieved Go To Floor Message");
            	log.info(goToFloorMessage);
            	
            	Elevator elevator = context.getElevators().get(goToFloorMessage.getElevatorId());
            	// send it to the floor in the message
            	elevator.goToFloor(goToFloorMessage.getDestinationFloor());
                break;
            case 6:
            	System.out.println("recieved click simulation message, sending to scheduler");
            	
                ElevatorClickSimulationMessage elevatorClickSimulationMessage = (ElevatorClickSimulationMessage) message;
               
                log.info("recieved click simulation message, sending to scheduler");
                log.info(elevatorClickSimulationMessage);
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
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
       
        log.info("Sending ElevatorStateMessage to scheduler");
        log.info(elevatorStateMessage.toString());
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }
    public void sendElevatorButton(int destinationFloor, int elevatorId){
    	System.out.println("Elevator button pressed, notifying scheduler");
    	log.info("Sending ElevatorButtonMessage to scheduler");
     
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date());
        
        log.info(elevatorButtonMessage);
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }
}

