package com.sysc3303.commons;

import java.net.InetAddress;

public abstract class MessageHandler{
    private CommunicationHandler communicationHandler;

    public void received(Message message){
        return;
    }
    public void setCommunicationHandler(CommunicationHandler communicationHandler){
        this.communicationHandler = communicationHandler;
    }

    public void send(Message message, InetAddress address, int port){
        communicationHandler.send(message, address, port);
    }
}

