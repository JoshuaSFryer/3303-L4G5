package com.sysc3303.scheduler;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class handles all the message sending to scheduler
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class SchedulerMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with floor and elevator
    private InetAddress     elevatorAddress;
    private InetAddress     floorAddress;
    private SchedulerSystem schedulerSystem;
    
    /**
     * The schedulerMessageHandler's constructer
     * @para receivePort
     * @para scheduler
     */
    static int elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
    static int floorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));

    public SchedulerMessageHandler(int receivePort, SchedulerSystem schedulerSystem){
        super(receivePort);
        this.schedulerSystem = schedulerSystem;
        //TODO currently for localhost this is how it looks
        try{
            elevatorAddress = floorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }
    /**
     * Handles three situations for scheduler which is receiving floorButtonMessage
     * ElevatorStateMessage and ElevatorButtonMessage.
     * @para message
     */
    @Override
    public synchronized void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // TODO what happens when you receive FloorButton
            	System.out.println("Received FloorButtonMessage from floor");
            	
            	FloorButtonMessage floorButtonMessage = (FloorButtonMessage)message;
            	
            	System.out.println(floorButtonMessage.toString());
            	
            	schedulerSystem.getScheduler().startFloorMessageHandler(message);
                break;
            case 3:
                // TODO what happens when you receive ElevatorState
            	System.out.println("Received ElevatorStateMessage from elevator");
            	
            	ElevatorStateMessage elevatorStateMessage = (ElevatorStateMessage)message;
            	
            	System.out.println(elevatorStateMessage.toString());

            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 4:
                // TODO what happens when you receive ElevatorButton
            	System.out.println("Received ElevatorButtonMessage from elevator");
            	
            	ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage)message;
            	
            	System.out.println(elevatorButtonMessage.toString());
                
            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    /**
     * @return goToFloorMessage
     */
    public void sendGoToFloor(GoToFloorMessage goToFloorMessage){
        send(goToFloorMessage, elevatorAddress, elevatorPort);
    }

    /**
     * @return floorArrialMessage
     */
    public void sendFloorArrival(FloorArrivalMessage floorArrivalMessage){
        send(floorArrivalMessage, floorAddress, floorPort);
    }
}
