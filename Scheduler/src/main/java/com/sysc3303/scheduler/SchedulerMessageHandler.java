package com.sysc3303.scheduler;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

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
    private Logger          log = Logger.getLogger(SchedulerMessageHandler.class);
    
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
            elevatorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("elevatorAddress"));
            floorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("floorAddress"));
        }catch(UnknownHostException e){
            e.printStackTrace();
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
            	FloorButtonMessage floorButtonMessage = (FloorButtonMessage)message;
  
            	log.info("Received FloorButtonMessage from floor");
            	log.info(floorButtonMessage);
            	
            	schedulerSystem.getScheduler().startFloorMessageHandler(message);
                break;
            case 3:
                // TODO what happens when you receive ElevatorState
            	ElevatorStateMessage elevatorStateMessage = (ElevatorStateMessage)message;
            	       
            	log.info("Received ElevatorStateMessage from elevator");
            	log.info(elevatorStateMessage);

            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 4:
                // TODO what happens when you receive ElevatorButton
            	ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage)message;
            	
            	log.info("Received ElevatorButtonMessage from elevator");
            	log.info(elevatorButtonMessage);
                
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
    	log.info("Sending GoToFloorMessage to elevator");
    	log.info(goToFloorMessage);
        send(goToFloorMessage, elevatorAddress, elevatorPort);
    }

    /**
     * @return floorArrialMessage
     */
    public void sendFloorArrival(FloorArrivalMessage floorArrivalMessage){
    	log.info("Sending FloorArrivalMessage to Floor");
    	log.info(floorArrivalMessage);
        send(floorArrivalMessage, floorAddress, floorPort);
    }
}
