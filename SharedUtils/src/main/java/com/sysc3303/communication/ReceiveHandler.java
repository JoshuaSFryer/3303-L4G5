package com.sysc3303.communication;

import com.sysc3303.commons.SerializationUtil;

/**
 * Manages the receive sockets for CommunicationHandler
 * On a receipt of a message the Receive handler forwards the message to the MessageHandler
 * Then starts a new Receive handler thread to keep listening
 *
 * @author Mattias Lightstone
 */
public class ReceiveHandler extends Thread{
    private final int MAX_PACKET_BYTES = 1000;

    private final SocketHandler socketHandler;
    private final SerializationUtil<Message> serializationUtil;
    private final MessageHandler messageHandler;
    private final int port;

    /**
     * Constructor with sockethandler initialization
     * On first creation of a receive handler assign a new socket
     * @param port the port to open for message receipt
     * @param messageHandler the message handler to forward received messages to
     */
    public ReceiveHandler(int port, MessageHandler messageHandler){
        serializationUtil = new SerializationUtil<>();
        socketHandler = new SocketHandler(port);
        this.messageHandler = messageHandler;
        this.port = port;
    }

    /**
     * Constructor using an already existing socket handler
     * @param port the port to open for message receipt
     * @param messageHandler the message handler to forward received messages to
     * @param socketHandler existing sockethandler to create receiveSocket
     */
    public ReceiveHandler(int port, MessageHandler messageHandler, SocketHandler socketHandler){
    	super("Receive Handler");
        serializationUtil = new SerializationUtil<>();
        this.socketHandler = socketHandler;
        this.messageHandler = messageHandler;
        this.port = port;
    }

    public void run(){
        // wait for packet to be received by socket
        byte[] data = socketHandler.waitForPacket(new byte[MAX_PACKET_BYTES], false);
        int length = socketHandler.getReceivePacketLength();
        // deserialize packet into a message object
        Message message = serializationUtil.deserialize(data, length);
        // Make and start a new receive handler with the same socket handler and port
        // While the current thread is handling the message the new thread will listen for packets
        ReceiveHandler receiveHandler = new ReceiveHandler(this.port, this.messageHandler, this.socketHandler);
        receiveHandler.start();
        // Send message to MessageHandler
        messageHandler.received(message);
    }
}
