package com.sysc3303.gui;

import com.sysc3303.communication.*;

public class GUIMessageHandler extends MessageHandler {
    private static GUIMessageHandler instance;

    //TODO: Configure static port numbers.

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
                System.out.println(moveMSG.toString());
                break;
            case 8: // floor update
                GUIFloorMessage floorMSG = (GUIFloorMessage) message;
                // Have the GUI do a thing here.
                System.out.println(floorMSG.toString());
                break;
            default:
                System.out.println("This message type is not handled by this module!");
        }
    }
}
