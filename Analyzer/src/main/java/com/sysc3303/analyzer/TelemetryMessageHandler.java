package com.sysc3303.analyzer;

import com.sysc3303.communication.Message;
import com.sysc3303.communication.MessageHandler;
import com.sysc3303.communication.TelemetryMessage;

public class TelemetryMessageHandler extends MessageHandler {
    private static TelemetryMessageHandler instance;
    private TelemetryMessageList messageList;
    private TelemetryMath math;

    public static TelemetryMessageHandler getInstance(){
        if (instance == null){
            instance = new TelemetryMessageHandler();
        }
        return instance;
    }

    private TelemetryMessageHandler(){
        super();
        messageList = new TelemetryMessageList();
    }


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
