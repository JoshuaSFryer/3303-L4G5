package com.sysc3303.floor;

import com.sysc3303.commons.*;
import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

public class FloorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress schedulerAddress;
    private InetAddress simulatorAddress;
    private FloorSystem floorSystem;

    static int schedulerPort;
    static int elevatorPort;
    static int floorPort;
    static int simulatorPort;

    static {
        Properties properties = new Properties();
        try{
            InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
            properties.loadFromXML(inputStream);

            schedulerPort = Integer.parseInt(properties.getProperty("schedulerPort"));
            elevatorPort = Integer.parseInt(properties.getProperty("elevatorPort"));
            floorPort = Integer.parseInt(properties.getProperty("floorPort"));
            simulatorPort = Integer.parseInt(properties.getProperty("simulatorPort"));
        }catch(FileNotFoundException e){
        }catch(IOException e){
        }
    }
    private static FloorMessageHandler instance;

    public static FloorMessageHandler getInstance(int receivePort, FloorSystem floorSystem){
        if (instance == null){
            return new FloorMessageHandler(receivePort, floorSystem);
        }
        return instance;
    }

    private FloorMessageHandler(int receivePort, FloorSystem floorSystem){
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
            case 0:
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
                FloorClickSimulationMessage floorClickSimulationMessage = (FloorClickSimulationMessage) message;
                floorSystem.buttonPress(floorClickSimulationMessage.getFloor(), floorClickSimulationMessage.getDirection());
                break;
            case 6:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                break;
            default:
                // TODO what happens when you get an invalid opcode
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
