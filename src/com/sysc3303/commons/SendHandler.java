package com.sysc3303.commons;

import java.net.InetAddress;

public class SendHandler extends Thread{

    private final SocketHandler socketHandler;
    private final SerializationUtil<Message> serializationUtil;
    private final Message message;
    private final int port;
    private final InetAddress address;

    public SendHandler(Message message, InetAddress address, int port){
        serializationUtil = new SerializationUtil<Message>();
        socketHandler = new SocketHandler();
        this.message = message;
        this.address = address;
        this.port = port;
    }

    public void run(){
        byte[] data = serializationUtil.serialize(message);
        socketHandler.sendSocket(data, address, port);
        socketHandler.closeSocket();
    }

}