package com.sysc3303.commons;

import com.sysc3303.communication.ConfigMessageHandler;
import com.sysc3303.communication.RabbitSubscriber;

import static java.lang.Thread.sleep;

/**
 * Subscribes to the configuration exchange on the rabbitmq server and waits for a configuration to be published
 * Once published it replaces the existing config with the new one and exits
 */
public class ConfigListener implements Runnable {

    /**
     * Runs the config listener.
     * Run this before the main loop in a module to block the module running until it has received a config.
     */
    @Override
    public void run() {
        String exchangeName = ConfigProperties.getInstance().getProperty("configExchangeName");
        ConfigMessageHandler configMessageHandler = new ConfigMessageHandler();

        Thread subscriber = new Thread(new RabbitSubscriber(
                configMessageHandler,
                exchangeName
        ));
        subscriber.run();

        // if the configuration isn't done yet wait 500 miliseconds and check again
        while(configMessageHandler.getCount() == 0){
            try{
                sleep(500);
            } catch (InterruptedException e){
            }
        }
        System.out.println("Configuration was updated");
    }
}