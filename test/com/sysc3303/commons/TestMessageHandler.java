package com.sysc3303.commons;

public class TestMessageHandler implements IMessageHandler{
    @Override
    public void received(Message message) {
        System.out.println("received message" + message);
    }

    public TestMessageHandler(){
    }
}
