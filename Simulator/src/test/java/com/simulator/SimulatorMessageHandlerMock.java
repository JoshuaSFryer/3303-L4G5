package com.simulator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.ElevatorClickSimulationMessage;
import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.communication.MessageHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimulatorMessageHandlerMock extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;

    static int floorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));
    static int elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));

    private static SimulatorMessageHandlerMock instance;

    public static SimulatorMessageHandlerMock getInstance(int receivePort){
        if (instance == null){
            instance = new SimulatorMessageHandlerMock(receivePort);
        }
        return instance;
    }

    private SimulatorMessageHandlerMock(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("Docker"))){
                elevatorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("elevatorDockerAddress"));
                floorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("floorDockerAddress"));
            }
            else{
                elevatorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("elevatorAddress"));
                floorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("floorAddress"));
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
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
