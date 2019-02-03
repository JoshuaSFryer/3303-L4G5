package com.sysc3303.commons;


import java.net.InetAddress;

/**
 * Manages send and receive sockets for Modules
 * Allows for messages to be sent between multiple CommunicationHandler Instances
 * On creation it opens a receive socket at the specified port as a thread
 * On request it starts a sendSocket in a new thread
 */
class CommunicationHandler{

    /**
     * Constructor
     * On construction a receive handler thread is immediately created and started
     * @param receivePort port number for receive socket
     * @param messageHandler the message handler that will have any messages received forwarded to it
     */
    public CommunicationHandler(int receivePort, MessageHandler messageHandler){
        ReceiveHandler receiveHandler = new ReceiveHandler(receivePort, messageHandler);
        receiveHandler.start();
    };

    /**
     * @param message Message object to be sent to another Communication handler
     * @param address The destination address for the message
     * @param port The destination port for the message
     */
    public void send(Message message, InetAddress address, int port){
        SendHandler sendHandler = new SendHandler(message, address, port);
        sendHandler.start();
    }
}
