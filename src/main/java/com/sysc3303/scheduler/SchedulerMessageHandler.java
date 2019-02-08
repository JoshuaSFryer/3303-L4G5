package com.sysc3303.scheduler;

import com.sysc3303.commons.ConfigProperty;
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
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;
    private Scheduler   scheduler;
    
    /**
     * The schedulerMessageHandler's constructer
     * @para receivePort
     * @para scheduler
     */
    static int elevatorPort = Integer.parseInt(ConfigProperty.getInstance().getProperty("elevatorPort"));
    static int floorPort = Integer.parseInt(ConfigProperty.getInstance().getProperty("floorPort"));

    public SchedulerMessageHandler(int receivePort, Scheduler scheduler){
        super(receivePort);
        this.scheduler = scheduler;
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
    	System.out.println("Received Message!");
  
        switch (message.getOpcode()){
            case 0:
                // TODO what happens when you receive FloorButton
            	System.out.println("Received FloorButtonMessage");
            	FloorButtonMessage floorButtonMessage = (FloorButtonMessage)message;
            	System.out.println(floorButtonMessage.toString());
            	
            	scheduler.startFloorMessageHandler(message);
           
            	GoToFloorMessage goToFloorMessage = scheduler.getGoToFloorMessage();
            	System.out.println("Sending Message to elevator");
            	System.out.println(goToFloorMessage.toString());
            	sendGoToFloor(goToFloorMessage);
                break;
            case 1:
                // Shouldn't have this on the scheduler
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // Shouldn't have this on the scheduler
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // TODO what happens when you receive ElevatorState
            	System.out.println("Received ElevatorStateMessage");
            	
            	ElevatorStateMessage elevatorStateMessage = (ElevatorStateMessage)message;
            	
            	System.out.println(elevatorStateMessage.toString());
            	//System.out.println(elevatorStateMessage.getElevatorVector().toString());
           
            	scheduler.startElevatorMessageHandler(message);
            	
            	if(elevatorStateMessage.getElevatorVector().currentFloor == elevatorStateMessage.getElevatorVector().targetFloor) {
            		FloorArrivalMessage floorArrivalMessage = scheduler.getFloorArrivalMessage();
            		
            		System.out.println("Sending Message to Floor");
                	System.out.println(floorArrivalMessage.toString());
                	
                	sendFloorArrival(floorArrivalMessage);
            	}
          
            	break;
            case 4:
                // TODO what happens when you receive ElevatorButton
            	System.out.println("Received ElevatorButtonMessage");
            	ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage)message;
            	System.out.println(elevatorButtonMessage.toString());
                   
            	scheduler.startElevatorMessageHandler(message);
            	break;
            case 5:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive ElevatorButtonSimulationMessage
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
