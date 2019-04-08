package com.sysc3303.communication;

import com.sysc3303.commons.ConfigProperties;

import java.util.Properties;

/**
 * Message handler used to handle configuration update messages
 */
public class ConfigMessageHandler extends MessageHandler{
    private int count;

    /**
     * Default constructor initializes a count variable to 0 to know how many valid messages it has received
     */
    public ConfigMessageHandler(){
        super();
        count = 0;
    }

    /**
     * Handles the receipt of a message
     * If the messages is not a Config Update the message is ignored
     * If it is a config update, the Node's configuration is updated, and count is incremented
     * @param message the message that is to be handled
     */
    @Override
    public void received(Message message){
        if (message.getOpcode() == 16){
            Properties properties = ((ConfigUpdateMessage) message).getProperties();
            ConfigProperties.getInstance().setProperties(properties);
            Thread.currentThread().interrupt();
            count++;
        }
    }

    public int getCount(){
        return count;
    }
}
