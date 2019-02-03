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

public class FloorMessageHandlerMock extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress schedulerAddress;
    private InetAddress simulatorAddress;

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

    private static FloorMessageHandlerMock instance;

    public static FloorMessageHandlerMock getInstance(int receivePort){
        if (instance == null){
            instance = new FloorMessageHandlerMock(receivePort);
        }
        return instance;
    }

    private FloorMessageHandlerMock(int receivePort){
        super(receivePort);
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
                System.out.println("Floor arrival method called");
                System.out.println(floorArrivalMessage);
                break;
            case 2:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatoirState
                break;
            case 4:
                // Shouldn't have this on the Floor
                // TODO what happens when you receive ElevatorButton
                break;
            case 5:
                // TODO what happens when you receive FloorButtonSimulationMessage
                FloorClickSimulationMessage floorClickSimulationMessage = (FloorClickSimulationMessage) message;
                System.out.println("FloorClickSimulatorMethodCalled");
                System.out.println(floorClickSimulationMessage);
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

