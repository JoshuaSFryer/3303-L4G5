package com.sysc3303.commons;

public class TestMessageHandler implements IMessageHandler{
    @Override
    public void received(Message message) {
        System.out.println("Received message\n" + message);
    }

    public TestMessageHandler(){
    }
}
