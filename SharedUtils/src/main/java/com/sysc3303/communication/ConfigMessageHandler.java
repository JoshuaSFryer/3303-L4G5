package com.sysc3303.communication;

import com.sysc3303.commons.ConfigProperties;

import java.util.Properties;

public class ConfigMessageHandler extends MessageHandler{
    private int count;

    public ConfigMessageHandler(){
        super();
        count = 0;
    }

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
