package com.sysc3303.commons;

import com.sysc3303.elevator.ElevatorVector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.Assert.fail;

public class CommunicationHandlerTest{
    @Test
    public void test1() throws InterruptedException{
        int elevatorPorts = 7000;
        int schedulerPort = 7001;
        int floorPort = 7002;

        // Three message Handlers
        TestMessageHandler elevatorMessageHandler = new TestMessageHandler(elevatorPorts);
        TestMessageHandler schedulerMessageHandler = new TestMessageHandler(schedulerPort);
        TestMessageHandler floorMessageHandler = new TestMessageHandler(floorPort);

        elevatorMessageHandler.sendElevatorStateMessage(new ElevatorVector(), 1);
        elevatorMessageHandler.sendElevatorButtonMessage(5, 1, new Date());
        schedulerMessageHandler.sendGoToFloorMessage(5);
        schedulerMessageHandler.sendFloorArrivalMessage(5, Direction.DOWN);
        floorMessageHandler.sendFloorButtonMessage(5, Direction.DOWN, new Date());

        sleep(4000);

        Assert.assertEquals(1, elevatorMessageHandler.callCount);
        Assert.assertEquals(3, schedulerMessageHandler.callCount);
        Assert.assertEquals(1, floorMessageHandler.callCount);
    }
}