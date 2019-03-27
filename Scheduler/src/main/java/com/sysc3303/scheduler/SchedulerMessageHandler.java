package com.sysc3303.scheduler;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ConfigUpdateHandler;
import com.sysc3303.communication.*;
import com.sysc3303.constants.Constants;

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
    static String schedulerQueueName = ConfigProperties.getInstance().getProperty("schedulerQueueName");
    static String telemetaryQueueName = ConfigProperties.getInstance().getProperty("telemetryQueueName");            	
    
    public SchedulerMessageHandler(int receivePort, SchedulerSystem schedulerSystem){
        super(receivePort);
        this.schedulerSystem = schedulerSystem;
        //TODO currently for localhost this is how it looks
        try{
            if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
                elevatorAddress = floorAddress = InetAddress.getLocalHost();
            }
            else{
                elevatorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("elevatorAddress"));
                floorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("floorAddress"));
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        RabbitReceiver rabbitReceiver = new RabbitReceiver(this, schedulerQueueName);
        (new Thread(rabbitReceiver, "elevator queue receiver")).start();
    }
    
    /**
     * Handles three situations for scheduler which is receiving floorButtonMessage
     * ElevatorStateMessage and ElevatorButtonMessage.
     * @para message
     */
    @Override
    public synchronized void received(Message message){
        super.received(message);
		long         buttonPressedTime; 
		long         messageArriveTime;
		long         nanoTime;
		int          secTime;
		RabbitSender rabbitSender;
        switch (message.getOpcode()){
            case 0:
                // What happens when you receive FloorButton
            	FloorButtonMessage floorButtonMessage = (FloorButtonMessage)message;
            	buttonPressedTime = floorButtonMessage.getPressedTime(); 
            	messageArriveTime = System.currentTimeMillis() * Constants.NANO_PER_MILLI;
            	nanoTime          = messageArriveTime - buttonPressedTime;
            	secTime           = (int)(nanoTime / Constants.NANO_PER_SEC);           
            	
            	log.info("Received FloorButtonMessage from floor");
            	log.info(floorButtonMessage);
            	
            	TelemetryFloorMessage telemetryFloorMessage = new TelemetryFloorMessage(secTime, nanoTime);
            	
            	rabbitSender = new RabbitSender(telemetaryQueueName, telemetryFloorMessage);
            	(new Thread(rabbitSender)).start();
            	
            	schedulerSystem.getScheduler().startFloorMessageHandler(message);
                break;
            case 3:
                // What happens when you receive ElevatorState
            	ElevatorStateMessage elevatorStateMessage = (ElevatorStateMessage)message;
            	       
            	log.info("Received ElevatorStateMessage from elevator");
            	log.info(elevatorStateMessage);

            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 4:
                // What happens when you receive ElevatorButton
            	ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage)message;
            	buttonPressedTime = elevatorButtonMessage.getPressedTime(); 
            	messageArriveTime = System.currentTimeMillis() * Constants.NANO_PER_MILLI;
            	nanoTime          = messageArriveTime - buttonPressedTime;
            	secTime           = (int)(nanoTime / Constants.NANO_PER_SEC);           
            	
            	log.info("Received ElevatorButtonMessage from elevator");
            	log.info(elevatorButtonMessage);
            	
            	TelemetryElevatorMessage telemetryElevatorMessage = new TelemetryElevatorMessage(secTime, nanoTime);
            	
            	rabbitSender = new RabbitSender(telemetaryQueueName, telemetryElevatorMessage);
            	(new Thread(rabbitSender)).start();
                
            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 11: 
            	StuckMessage imStuckMessage = (StuckMessage)message;

            	log.info("Received StuckMessage from elevator");
            	log.info(imStuckMessage);
            	
            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 12:
            	UnStuckMessage unStuckMessage = (UnStuckMessage)message;
 
            	log.info("Received UnStuckMessage from elevator");
            	log.info(unStuckMessage);
            	
            	schedulerSystem.getScheduler().startElevatorMessageHandler(message);
            	break;
            case 16:
                (new Thread(new ConfigUpdateHandler((ConfigUpdateMessage) message))).start();
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
