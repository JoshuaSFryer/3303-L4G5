package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ElevatorMessageHandlerMock extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    static ElevatorMessageHandlerMock instance;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));

    public static ElevatorMessageHandlerMock getInstance(int receiverPort){
        if (instance == null){
            return new ElevatorMessageHandlerMock(receiverPort);
        }
        return instance;
    }

    private InetAddress schedulerAddress;

    private ElevatorMessageHandlerMock(int receivePort){
        super(receivePort);
        //TODO currently for localhost this is how it looks
        try{
            schedulerAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("schedulerDockerAddress"));
        }catch(UnknownHostException e){
        }
    }

    @Override
    public void received(Message message){
        // TODO Whatever functionality you want when your receive a message
        switch (message.getOpcode()){
            case 0:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButton
                break;
            case 1:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorArrival
                break;
            case 2:
                // TODO what happens when you receive GoToFloor
                GoToFloorMessage goToFloorMessage = (GoToFloorMessage) message;
                System.out.println("Received Go to floor command");
                System.out.println(goToFloorMessage);
                break;
            case 3:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive ElevatorState
                break;
            case 4:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive ElevatorButton
                break;
            case 5:
                // Shouldn't have this on the elevator
                // TODO what happens when you receive FloorButtonSimulationMessage
                break;
            case 6:
                // TODO what happens when you receive ElevatorButtonSimulationMessage
                ElevatorClickSimulationMessage elevatorClickSimulationMessage = (ElevatorClickSimulationMessage) message;
                System.out.println("Received Elevator Button Simulation Message");
                System.out.println(elevatorClickSimulationMessage);
                break;
            default:
                // TODO what happens when you get an invalid upcode
        }
    }

    // TODO Rename this if you would like to
    public void sendElevatorState(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }
    public void sendElevatorButton(int destinationFloor, int elevatorId){
        System.out.println("\nElevator Button Clicked for floor " + destinationFloor);
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date(), 1000);
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }
}
