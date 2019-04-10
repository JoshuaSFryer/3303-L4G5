package com.sysc3303.commons;

import com.sysc3303.communication.ConfigUpdateMessage;
import com.sysc3303.communication.RabbitPublisher;
import com.sysc3303.communication.RabbitSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Allows for the dynamic updating of configurations in a multinodal system
 * Sends a specific configuration changes to all of the nodes through their message queue
 */

public class ConfigUpdater {

    /**
     * get a new instance of the config updater
     * @return
     */
    public static ConfigUpdater getNewInstance() {
        return new ConfigUpdater();
    }

    /**
     * Sends an update to each of the queues defined in list
     * @param properties the properties object that should be distributed to replace the one in the files
     * @param exchangeName the name of the exchange to publish the properties to
     */
    public void updateConfigs(Properties properties, String exchangeName){
        ConfigUpdateMessage message = new ConfigUpdateMessage(properties);

        (new Thread(new RabbitPublisher(exchangeName, message))).start();
    }

    private ConfigUpdater(){
    }

}
