package com.sysc3303.communication;

import java.net.InetAddress;

/**
 * @author Mattias Lightstone
 * Abstract MessageHandler
 * Used by the application to send functions to the Communication Handler and subsequently to another node
 * Node->MessageHander->CommunicationHandler->socket----------->socket2->CommunicationHandler2->MessageHandler2->Node2
 */
public abstract class MessageHandler{
    // The communication handler used to send and receive Messages
    protected CommunicationHandler communicationHandler;

    public MessageHandler(int receivePort){
        this.communicationHandler = new CommunicationHandler(receivePort, this);
    }

    /**
     * Called by Communication handler when a message is received
     * Add in the actions that the module should take on package receive
     * @throws InterruptedException 
     */
    public void received(Message message){
        // Rename the thread to something useful
        Thread.currentThread().setName("Handling " + message.getSummary());
    }

    /**
     * Uses the Communication Handler to send a Message to another Communication Handler
     * @param message message to be sent
     * @param address destination address
     * @param port destination port
     */
    public void send(Message message, InetAddress address, int port){
        communicationHandler.send(message, address, port);
    }
}

