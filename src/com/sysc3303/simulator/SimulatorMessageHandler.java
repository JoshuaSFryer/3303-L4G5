package com.sysc3303.simulator;

import com.sysc3303.commons.*;
import com.sysc3303.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class SimulatorMessageHandler extends MessageHandler{
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;

    static int schedulerPort;
    static int elevatorPort;
    static int floorPort;
    static int simulatorPort;
    static SimulatorMessageHandler instance;

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

    public static SimulatorMessageHandler getInstance(int receivePort){
        if (instance == null){
            instance = new SimulatorMessageHandler(receivePort);
        }
        return instance;
    }

    public SimulatorMessageHandler(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            floorAddress = elevatorAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
        }
    }


    @Override
    public void received(Message message){
        // TODO Whatever functionality you want when your receive a message

        switch (message.getOpcode()){
            case 0:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive GoToFloor
                break;
            case 3:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // Shouldn't have this on the simulator
                // TODO what happens when you receive ElevatorButton
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

    public void sendFloorButtonClickSimulation(int floor, Direction direction){
        FloorClickSimulationMessage floorClickSimulationMessage = new FloorClickSimulationMessage(floor, direction);
        send(floorClickSimulationMessage, floorAddress, floorPort);
    }
    public void sendElevatorButtonClickSimulation(int floor, int elevatorId){
        ElevatorClickSimulationMessage elevatorButtonClickSimulation = new ElevatorClickSimulationMessage(floor, elevatorId);
        send(elevatorButtonClickSimulation, elevatorAddress, elevatorPort);
    }
}
