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
        int port1 = 7000;
        int port2 = 7001;
        // Two message Handlers
        TestMessageHandler messageHandler1 = new TestMessageHandler();
        TestMessageHandler messageHandler2 = new TestMessageHandler();
        // Two Communication Handlers
        CommunicationHandler communicationHandler1 = new CommunicationHandler(port1, messageHandler1);
        CommunicationHandler communicationHandler2 = new CommunicationHandler(port2, messageHandler2);
        // Make a message and send it from one to the other
        FloorArrivalMessage message1 = new FloorArrivalMessage(1, Direction.DOWN);
        FloorButtonMessage message2 = new FloorButtonMessage(1, Direction.DOWN, new Date());
        GoToFloorMessage message3 = new GoToFloorMessage(1);
        ElevatorStateMessage message4 = new ElevatorStateMessage(new ElevatorVector(), 10);
        ElevatorButtonMessage message5 = new ElevatorButtonMessage(5, 1, new Date());
        try {
            communicationHandler1.send(message1, InetAddress.getLocalHost(), port2);
        } catch (UnknownHostException e){
            fail();
        }
        try {
            communicationHandler2.send(message2, InetAddress.getLocalHost(), port1);
        } catch (UnknownHostException e){
            fail();
        }
        try {
            communicationHandler2.send(message3, InetAddress.getLocalHost(), port1);
        } catch (UnknownHostException e){
            fail();
        }
        try {
            communicationHandler1.send(message4, InetAddress.getLocalHost(), port2);
        } catch (UnknownHostException e){
            fail();
        }
        try {
            communicationHandler1.send(message5, InetAddress.getLocalHost(), port2);
        } catch (UnknownHostException e){
            fail();
        }
        Assert.assertEquals(2, messageHandler1.callCount);
        Assert.assertEquals(3, messageHandler2.callCount);
        sleep(15000);
    }
}