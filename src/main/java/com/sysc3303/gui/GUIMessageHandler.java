package com.sysc3303.gui;

import com.sysc3303.communication.GUIElevatorMoveMessage;
import com.sysc3303.communication.GUIFloorMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.communication.MessageHandler;

public class GUIMessageHandler extends MessageHandler {
    private static GUIMessageHandler instance;

    public static GUIMessageHandler getInstance(int receivePort) {
        if (instance == null) {
            instance = new GUIMessageHandler(receivePort);
        }
        return instance;
    }

    private GUIMessageHandler(int receivePort) {
        super(receivePort);

    }

    @Override
    public void received(Message message) {
        switch(message.getOpcode()) {
            case 7: // elevator update
                GUIElevatorMoveMessage moveMSG = (GUIElevatorMoveMessage) message;
                // Have the GUI do a thing here.
                break;
            case 8: // floor update
                GUIFloorMessage floorMSG = (GUIFloorMessage) message;
                // Have the GUI do a thing here.
                break;
            default:
                System.out.println("This message type is not handled by this module!");
        }
    }
}
