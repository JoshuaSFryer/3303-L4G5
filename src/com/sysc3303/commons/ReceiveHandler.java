package com.sysc3303.commons;

import java.io.IOException;

public class ReceiveHandler extends Thread{


    private final SocketHandler socketHandler;
    private final SerializationUtil<Message> serializationUtil;
    private final IMessageHandler messageHandler;
    private final int port;

    public ReceiveHandler(int port, IMessageHandler messageHandler){
        serializationUtil = new SerializationUtil<>();
        socketHandler = new SocketHandler(port);
        this.messageHandler = messageHandler;
        this.port = port;
    }

    public void run(){
        byte[] data = socketHandler.waitForPacket(new byte[300], false);
        int length = socketHandler.getReceivePacketLength();
        Message message = serializationUtil.deserialize(data, length);
        ReceiveHandler receiveHandler = new ReceiveHandler(this.port, this.messageHandler);
        receiveHandler.start();
        messageHandler.received(message);
    }
}
