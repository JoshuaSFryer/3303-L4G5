package com.sysc3303.commons;

import com.sysc3303.elevator.ElevatorVector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class TestMessageHandler extends MessageHandler{
    public int callCount = 0;
    private CommunicationHandler communicationHandler;

    // in practice this would have many ports for elevator
    private int elevatorPorts = 7000;
    private int schedulerPort = 7001;
    private int floorPort = 7002;
    private InetAddress address;

    public TestMessageHandler(){
        try {
            this.address = InetAddress.getLocalHost();
        } catch (UnknownHostException e){
            System.out.println("Could not get local host");
        }

    }

    @Override
    public void received(Message message) {
        System.out.println("Received message\n" + message);
        callCount++;
    }

    public void sendFloorArrivalMessage(int floor, Direction direction){
        FloorArrivalMessage message = new FloorArrivalMessage(floor, direction);
        communicationHandler.send(message, this.address, floorPort);
    }

    public void sendFloorButtonMessage(int floor, Direction direction, Date time){
        FloorButtonMessage message = new FloorButtonMessage(floor, direction, time);
        communicationHandler.send(message, this.address, schedulerPort);
    }

    public void sendGoToFloorMessage(int destinationFloor){
        GoToFloorMessage message = new GoToFloorMessage(destinationFloor);
        communicationHandler.send(message, this.address, elevatorPorts);
    }

    public void sendElevatorStateMessage(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage message = new ElevatorStateMessage(elevatorVector, elevatorId);
        communicationHandler.send(message, this.address, schedulerPort);
    }

    public void sendElevatorButtonMessage(int destinationFloor, int elevatorId, Date time){
        ElevatorButtonMessage message = new ElevatorButtonMessage(destinationFloor, elevatorId, time);
        communicationHandler.send(message, this.address, schedulerPort);
    }
}
