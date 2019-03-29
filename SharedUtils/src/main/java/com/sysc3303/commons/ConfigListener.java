package com.sysc3303.commons;

import com.sysc3303.communication.ConfigMessageHandler;
import com.sysc3303.communication.RabbitSubscriber;

import static java.lang.Thread.sleep;

public class ConfigListener implements Runnable {

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