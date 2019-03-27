package com.sysc3303.communication;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ConfigUpdater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.Thread.sleep;

public class ConfigUpdateTest {
    @Before
    public void setUp(){
        MessageHandler handler = new TestConfigUpdateMessageHandler(2000);
        RabbitReceiver receiver = new RabbitReceiver(handler, "scheduler");
        (new Thread(receiver)).start();
    }
    @Test
    public void testSimpleConfigChange(){
        // get value before update
        String beforeUpdate = ConfigProperties.getInstance().getProperty("schedulerQueueName");

        // update
        ConfigUpdater updater = ConfigUpdater.getInstance();
        String[][] configUpdates = {
                {
                    "schedulerQueueName",
                        "sc"
                }
        };
        updater.updateConfigs(configUpdates);

        try{
            sleep(1000);
        } catch(InterruptedException e){
        }

        String afterUpdate = ConfigProperties.getInstance().getProperty("schedulerQueueName");
        // get value after update
        Assert.assertNotEquals(afterUpdate, beforeUpdate);
    }
}
