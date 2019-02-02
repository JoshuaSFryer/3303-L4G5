package com.sysc3303.commons;

import java.net.InetAddress;

class CommunicationHandler{

    public CommunicationHandler(int receivePort, MessageHandler messageHandler){
        ReceiveHandler receiveHandler = new ReceiveHandler(receivePort, messageHandler);
        receiveHandler.start();
    };

    public void send(Message message, InetAddress address, int port){
        SendHandler sendHandler = new SendHandler(message, address, port);
        sendHandler.start();
    }
}
