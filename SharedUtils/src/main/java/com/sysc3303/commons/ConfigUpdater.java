package com.sysc3303.commons;

import com.sysc3303.communication.ConfigUpdateMessage;
import com.sysc3303.communication.RabbitSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows for the dynamic updating of configurations in a multinodal system
 * Sends a specific configuration changes to all of the nodes through their message queue
 */

public class ConfigUpdater {

    private static final String[] queueNameProperties = {
            "floorQueueName",
            "elevatorQueueName",
            "schedulerQueueName",
            "simulatorQueueName",
            "guiQueueName",
            "analyzerQueueName"
    };
    private static ConfigUpdater instance;
    /**
     * The list of queues that need to have the message sent to them
     */
    private static List<String> queueNames;
    // method that updates the configuration based on values

    /**
     * get a new instance of the config updater
     * @return
     */
    public static ConfigUpdater getInstance() {
        if(instance == null){
            instance = new ConfigUpdater();
            queueNames = new ArrayList<>();
            for(int i=0; i<queueNameProperties.length; i++){
                queueNames.add(ConfigProperties.getInstance().getProperty(queueNameProperties[i]));
            }
        }
        return instance;
    }

    /**
     * Sends an update to each of the queues defined in list
     * @param configChanges the array containing the properties and their values that the nodes should change
     */
    public void updateConfigs(String[][] configChanges){
        ConfigUpdateMessage message = new ConfigUpdateMessage(configChanges);
        for (String name: queueNames){
            (new Thread(new RabbitSender(name, message))).start();
        }
    }

    private ConfigUpdater(){
    }

}
