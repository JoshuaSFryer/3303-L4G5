package com.sysc3303.communication;

import com.sysc3303.commons.SerializationUtil;

import java.net.InetAddress;

/**
 * Handles all send sockets used in the Communication Handler
 * Extends Thread, allowing for send socket threads to be created on demand
 * @author Mattias Lightstone
 */
public class SendHandler extends Thread{

    private final SocketHandler socketHandler;
    private final SerializationUtil<Message> serializationUtil;
    private final Message message;
    private final int port;
    private final InetAddress address;

    /**
     * Constructor creates a new serialization util and a new socket handler
     * @param message the message to be sent
     * @param port the destination port for the message
     * @param address the destination address for the message
     */
    public SendHandler(Message message, InetAddress address, int port){
        serializationUtil = new SerializationUtil<Message>();
        socketHandler = new SocketHandler();
        this.message = message;
        this.address = address;
        this.port = port;
    }

    /**
     * Opens a new socket, serializes a message, sends it and closes the socket
     */
    public void run(){
        byte[] data = serializationUtil.serialize(message);
        socketHandler.sendSocket(data, address, port);
        socketHandler.closeSocket();
    }

}