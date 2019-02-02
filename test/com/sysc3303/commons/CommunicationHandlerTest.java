package com.sysc3303.commons;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.fail;

public class CommunicationHandlerTest{
    @Test
    public void test1(){
        // Two message Handlers
        TestMessageHandler messageHandler1 = new TestMessageHandler();
        TestMessageHandler messageHandler2 = new TestMessageHandler();
        // Two Communication Handlers
        CommunicationHandler communicationHandler1 = new CommunicationHandler(3000, messageHandler1);
        CommunicationHandler communicationHandler2 = new CommunicationHandler(5000, messageHandler2);
        // Make a message and send it from one to the other
        FloorArrivalMessage message = new FloorArrivalMessage(1, Direction.DOWN);
        try {
            communicationHandler1.send(message, InetAddress.getLocalHost(), 5000);
        } catch (UnknownHostException e){
            fail();
        }
    }
}