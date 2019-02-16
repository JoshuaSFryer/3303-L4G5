package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.*;

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

    private InetAddress     schedulerAddress;
    private InetAddress     uiAddress;
    private ElevatorSystem  context;
    private Logger          log = Logger.getLogger(ElevatorMessageHandler.class);

    // Read ports from the configuration file.
    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    static int uiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));


    /**
     * Create an instance of this handler if one does not exist. Whether a new
     * instance is created or not, return a reference to the instance.
     * @param receivePort   The port for this handler to listen on.
     * @param context       The parent Elevator.
     * @return              A reference to this handler.
     */
    public static ElevatorMessageHandler getInstance(int receivePort, ElevatorSystem context){
        if (instance == null){
            instance = new ElevatorMessageHandler(receivePort, context);
        }
        return instance;
    }

    /**
     * Class constructor.
     * @param receivePort   The port for this handler to listen on.
     * @param context       The parent Elevator.
     */
    private ElevatorMessageHandler(int receivePort, ElevatorSystem context){
        super(receivePort);
        this.context = context;
        try{
            if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
                schedulerAddress = InetAddress.getLocalHost();
            }
            else{
                schedulerAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("schedulerAddress"));
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    @Override
    public void received(Message message){
        super.received(message);
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

    public void updateUI(int elevatorID, int currentFloor, Direction dir, boolean open) {
        GUIElevatorMoveMessage msg = new GUIElevatorMoveMessage(elevatorID, currentFloor, dir, open);
        send(msg, uiAddress, uiPort);
    }
}

