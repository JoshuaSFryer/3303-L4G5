package com.sysc3303.scheduler;

import com.sysc3303.commons.*;
import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class SchedulerMessageHandlerMock extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with floor and elevator
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;

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

    public SchedulerMessageHandlerMock(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            elevatorAddress = floorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }

    @Override
    public void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // TODO what happens when you receive FloorButton
                FloorButtonMessage floorButtonMessage = (FloorButtonMessage) message;
                System.out.println("Received Floor button message");
                System.out.println(floorButtonMessage);
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
                ElevatorStateMessage elevatorStateMessage = (ElevatorStateMessage) message;
                System.out.println("Received Elevator state");
                System.out.println(elevatorStateMessage);
                break;
            case 4:
                // TODO what happens when you receive ElevatorButton
                ElevatorButtonMessage elevatorButtonMessage = (ElevatorButtonMessage) message;
                System.out.println("Received Elevetor Button Message");
                System.out.println(elevatorButtonMessage);
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

    // TODO Rename this if you would like to
    public void sendGoToFloor(int floor){
        GoToFloorMessage goToFloorMessage = new GoToFloorMessage(floor);
        send(goToFloorMessage, elevatorAddress, elevatorPort);
    }

    // TODO Rename this if you would like to
    public void sendFloorArival(int floor, Direction direction){
        FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(floor, direction);
        send(floorArrivalMessage, floorAddress, floorPort);
    }
}
