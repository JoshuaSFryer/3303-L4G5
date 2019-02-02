package com.sysc3303.commons;

import java.net.InetAddress;

public abstract class MessageHandler{
    protected CommunicationHandler communicationHandler;

    public MessageHandler(int receivePort){
        this.communicationHandler = new CommunicationHandler(receivePort, this);
    }

    public void received(Message message){
        return;
    }

    public void send(Message message, InetAddress address, int port){
        communicationHandler.send(message, address, port);
    }
}

