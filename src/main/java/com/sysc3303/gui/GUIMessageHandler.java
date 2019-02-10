package com.sysc3303.gui;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.*;


/**
 * GUIMessageHandler handles messages sent to and from the GUI.
 * It follows the singleton pattern, so only one should ever exist at any time.
 *
 */
public class GUIMessageHandler extends MessageHandler {
    private static GUIMessageHandler instance;
    static int guiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));

    public static GUIMessageHandler getInstance() {
        if (instance == null) {
            instance = new GUIMessageHandler();
        }
        return instance;
    }

    private GUIMessageHandler() {
        super(guiPort);
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
