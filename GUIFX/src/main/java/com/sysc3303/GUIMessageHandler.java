package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.*;


/**
 * GUIMessageHandler handles messages sent to and from the GUI.
 * It follows the singleton pattern, so only one should ever exist at any time,
 * created by the GUI thread.
 *
 * When an event occurs that should be reflected in the GUI, the responsible
 * subsystem (Elevator or Floor) should send a message to this handler, which
 * will call the update methods on its parent GUI.
 */
public class GUIMessageHandler extends MessageHandler {
    private static GUIMessageHandler instance;
    private UserInterface context;
    static int guiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));

    public static GUIMessageHandler getInstance(UserInterface context) {
        if (instance == null) {
            System.out.println("GUIHandler: Binding port" + guiPort);
            instance = new GUIMessageHandler(context);
            String guiQueueName = ConfigProperties.getInstance().getProperty("guiQueueName");
            RabbitReceiver receiver = new RabbitReceiver(instance, guiQueueName);
            new Thread(receiver).start();
        }
        return instance;
    }

    private GUIMessageHandler(UserInterface context) {
        super(guiPort);
        this.context = context;
    }

    @Override
    public void received(Message message) {
        switch(message.getOpcode()) {

            case 7: // elevator update
                GUIElevatorMoveMessage moveMSG = (GUIElevatorMoveMessage) message;
                // Have the GUI do a thing here.
                context.moveElevator(moveMSG);
                break;

            case 8: // floor update
                GUIFloorMessage floorMSG = (GUIFloorMessage) message;
                // Have the GUI do a thing here.
                context.pressFloorButton(floorMSG);
                break;

            default:
                System.out.println("This message type is not handled by this module!");
        }
    }


    public void sendElevatorCarPress(GUIElevatorCarMessage message) {
        String elevatorQueueName = ConfigProperties.getInstance().getProperty("elevatorQueueName");
        RabbitSender sender = new RabbitSender(elevatorQueueName, message);
        new Thread(sender).start();
    }

    public void sendErrorToScheduler(GUIElevatorMoveMessage message){
        String schedulerQueueName = ConfigProperties.getInstance().getProperty("schedulerQueueName");
        RabbitSender sender = new RabbitSender(schedulerQueueName, message);
        new Thread(sender).start();
    }
}
