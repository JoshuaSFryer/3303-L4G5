package com.sysc3303.analyzer;

import com.sysc3303.communication.ConfigUpdateMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.communication.MessageHandler;
import com.sysc3303.communication.TelemetryMessage;

/**
 * Message handler for handling the reception of Telemetry data
 *
 */

public class TelemetryMessageHandler extends MessageHandler {
    private static TelemetryMessageHandler instance;
    private TelemetryMessageList messageList;
    private TelemetryMath math;

    /**
     * If there is an existing instance return that, if not make a new one and return it
     * @return an instance of TelemetryMessageHandler
     */
    public static TelemetryMessageHandler getInstance(){
        if (instance == null){
            instance = new TelemetryMessageHandler();
        }
        return instance;
    }

    /**
     * Private constructor prevents use outside of the class and ensures only one instance exists at a time
     */
    private TelemetryMessageHandler(){
        super();
        messageList = new TelemetryMessageList();
    }


    /**
     * Performs the appropriate action to process a message that is received
     * @param message the message to be processed
     */
    public void received(Message message){
        super.received(message);
        switch(message.getOpcode()){
            case 13:
                messageList.addElevatorButtonTime(((TelemetryMessage) message).getNanoSecondTime());
                break;
            case 14:
                messageList.addFloorButtonTime(((TelemetryMessage) message).getNanoSecondTime());
                break;
            case 15:
                messageList.addArrivalTime(((TelemetryMessage) message).getNanoSecondTime());
                break;
        }
    }

}

