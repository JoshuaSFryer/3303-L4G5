package com.sysc3303.scheduler;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SchedulerMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with floor and elevator
    final int elevatorPort = 7000;
    final int floorPort = 7001;
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;
    private Scheduler   scheduler;
    
    public SchedulerMessageHandler(int receivePort, Scheduler scheduler){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            elevatorAddress = floorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public synchronized void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // TODO what happens when you receive FloorButton
            	System.out.println("Recieved FloorButtonMessage");
            	FloorButtonMessage floorButtonMessage = (FloorButtonMessage)message;
            	System.out.println(floorButtonMessage.toString());
            	
            	scheduler.startFloorMessageHandler(message);
            	try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
            	System.out.println("Recieved ElevatorStateMessage");
            	ElevatorStateMessage ElevatorStateMessage = (ElevatorStateMessage)message;
            	System.out.println(ElevatorStateMessage.toString());
            	
            	scheduler.startElevatorMessageHandler(message);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	break;
            case 4:
                // TODO what happens when you receive ElevatorButton
            	System.out.println("Recieved ElevatorButtonMessage");
            	ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage)message;
            	System.out.println(elevatorButtonMessage.toString());
                   
            	scheduler.startElevatorMessageHandler(message);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	FloorArrivalMessage floorArrivalMessage = scheduler.getFloorArrivalMessage();
            	System.out.println("Sending Message to Floor");
            	System.out.println(floorArrivalMessage.toString());
            	sendFloorArrival(floorArrivalMessage);
            	break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    // TODO Rename this if you would like to
    public void sendGoToFloor(GoToFloorMessage goToFloorMessage){
        send(goToFloorMessage, elevatorAddress, elevatorPort);
    }

    // TODO Rename this if you would like to
    public void sendFloorArrival(FloorArrivalMessage floorArrivalMessage){
        send(floorArrivalMessage, floorAddress, floorPort);
    }
}
