package com.sysc3303.simulator;

import com.sysc3303.commons.*;
import com.sysc3303.communication.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimulatorMessageHandler extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress elevatorAddress;
    private InetAddress floorAddress;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    static int floorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));
    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));
    static int elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
    static SimulatorMessageHandler instance;

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
    }

    @Override
    public void received(Message message){
        super.received(message);
        switch (message.getOpcode()){
            case 1:
                // What happens when you receive FloorArrival
                FloorArrivalMessage floorArrivalMessage = (FloorArrivalMessage) message;
                FloorReceiver.getInstance().receiveElevatorArrival(floorArrivalMessage.getFloor(),
                        floorArrivalMessage.getCurrentDirection(),
                        floorArrivalMessage.getElevatorId());
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
