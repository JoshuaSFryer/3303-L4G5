package com.sysc3303.commons;

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
        // Two message Handlers
        TestMessageHandler messageHandler1 = new TestMessageHandler();
        TestMessageHandler messageHandler2 = new TestMessageHandler();
        // Two Communication Handlers
        CommunicationHandler communicationHandler1 = new CommunicationHandler(7000, messageHandler1);
        CommunicationHandler communicationHandler2 = new CommunicationHandler(7001, messageHandler2);
        // Make a message and send it from one to the other
        FloorArrivalMessage message1 = new FloorArrivalMessage(1, Direction.DOWN);
        FloorButtonMessage message2 = new FloorButtonMessage(1, Direction.DOWN, new Date());
        try {
            communicationHandler1.send(message1, InetAddress.getLocalHost(), 7001);
        } catch (UnknownHostException e){
            fail();
        }
        try {
            communicationHandler2.send(message2, InetAddress.getLocalHost(), 7000);
        } catch (UnknownHostException e){
            fail();
        }
        sleep(30000);
    }
}