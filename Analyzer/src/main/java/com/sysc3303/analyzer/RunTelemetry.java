package com.sysc3303.analyzer;

import com.sysc3303.commons.ConfigListener;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.RabbitReceiver;

/**
 * Simple analyzer for Telemetry data
 * Run to receive telemetry data from RabbitMQ
 * Current version will print the time and also compute the mean and variance of times
 */
public class RunTelemetry {

    public static void main(String[] args){
        if(args.length > 0){
            if(args[0] .equals("config")){
                new ConfigListener().run();
            }
        }
        String telemetryQueueName = ConfigProperties.getInstance().getProperty("telemetryQueueName");
        // Create a telemetry message handler
        TelemetryMessageHandler messageHandler = TelemetryMessageHandler.getInstance();
        // Create a receiver to listen to the telemetry queue in RabbitMQ
        RabbitReceiver receiver = new RabbitReceiver(messageHandler, telemetryQueueName);

        // Create the receiver thread and start it
        (new Thread(receiver)).start();
    }
}
