package com.sysc3303.communication;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class TestMessageHandler extends MessageHandler{
    public int callCount = 0;

    // in practice this would have many ports for elevator
    private int elevatorPorts = 7000;
    private int schedulerPort = 7001;
    private int floorPort = 7002;
    private InetAddress address;

    public TestMessageHandler(int receivePort){
        super(receivePort);
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

        if (message.getOpcode() == 16){
            System.out.println("Received Properties File message");
        }
    }

    public void sendFloorArrivalMessage(int floor, Direction direction, int elevatorId){
        FloorArrivalMessage message = new FloorArrivalMessage(floor, direction, elevatorId);
        send(message, this.address, floorPort);
    }

    public void sendFloorButtonMessage(int floor, Direction direction, Date time){
        FloorButtonMessage message = new FloorButtonMessage(floor, direction, time, 1000);
        send(message, this.address, schedulerPort);
    }

    public void sendGoToFloorMessage(int destinationFloor, int elevatorId){
        GoToFloorMessage message = new GoToFloorMessage(destinationFloor, elevatorId);
        send(message, this.address, elevatorPorts);
    }

    public void sendElevatorStateMessage(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage message = new ElevatorStateMessage(elevatorVector, elevatorId);
        send(message, this.address, schedulerPort);
    }

    public void sendElevatorButtonMessage(int destinationFloor, int elevatorId, Date time){
        ElevatorButtonMessage message = new ElevatorButtonMessage(destinationFloor, elevatorId, time, 1000);
        send(message, this.address, schedulerPort);
    }
}
