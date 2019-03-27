package com.sysc3303.commons;

import com.sysc3303.communication.ConfigUpdateMessage;

public class ConfigUpdateHandler implements Runnable{

    private String[][] configChanges;

    public ConfigUpdateHandler(ConfigUpdateMessage message){
        configChanges =  message.getConfigChanges();
    }

    public void run(){
        for(int i=0; i<configChanges.length; i++){
            String[] change = configChanges[i];
            ConfigProperties.getInstance().setProperty(change[0], change[1]);
        }
    }
}
