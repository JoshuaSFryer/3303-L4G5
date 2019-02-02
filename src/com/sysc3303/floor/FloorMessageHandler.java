package com.sysc3303.floor;

import com.sysc3303.commons.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class FloorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    final int schedulerPort = 7002;
    final int simulatorPort = 7003;
    private InetAddress schedulerAddress;
    private InetAddress simulatorAddress;
    private FloorSystem floorSystem;


    public FloorMessageHandler(int receivePort, FloorSystem floorSystem){
        super(receivePort);
        this.floorSystem = floorSystem;
        //TODO currently for localhost this is how it looks
        try{
            schedulerAddress = simulatorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public synchronized void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case OpCodes.FLOOR_BUTTON.getOpcode():
                // Shouldn't have this on the Floor
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // TODO what happens when you receive FloorArrival
            	FloorArrivalMessage floorArrivalMessage = (FloorArrivalMessage) message;
            	try {
            			floorSystem.floorArrival(floorArrivalMessage.getFloor(), floorArrivalMessage.getCurrentDirection());
            	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
                break;
            case 2:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorButton
                break;
            case 5:
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    public void sendFloorButton(int floor, Direction direction){
        FloorButtonMessage floorButtonMessage = new FloorButtonMessage(floor, direction, new Date());
        send(floorButtonMessage, schedulerAddress, schedulerPort);
    }
    
    public void sendFloorArrival(int floor, Direction direction) {
    	FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(floor, direction);
    	send(floorArrivalMessage, simulatorAddress, simulatorPort);
    }
}
