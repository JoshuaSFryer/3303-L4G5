package com.sysc3303.analyzer;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.RabbitReceiver;

public class TelemetryReceiver {


    public static void main(String[] args){
        String telemetryQueueName = ConfigProperties.getInstance().getProperty("telemetryQueueName");
        TelemetryMessageHandler messageHandler = TelemetryMessageHandler.getInstance();
        RabbitReceiver receiver = new RabbitReceiver(messageHandler, telemetryQueueName);

        (new Thread(receiver)).start();
    }
}



