package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;

import java.awt.*;

import com.sysc3303.communication.*;
import javafx.scene.control.Button;

/**
 * ErrorButtons represents a button that can be clicked to force a fault in the
 * elevator that it is associated with. They are always generated in pairs,
 * one that causes a door to stick and another that causes an elevator to stick.
 */
public class ErrorButtons extends Button {
    int elevatorID;
    String type;
//    static String errorQueue = ConfigProperties.getInstance().getProperty("schedulerQueueName");
    static String errorQueue = ConfigProperties.getInstance().getProperty("elevatorQueueName");

    int waitTime = Integer.parseInt(ConfigProperties.getInstance().getProperty("timeBetweenFloors"));

    ErrorButtons (int elevatorID, String str, String type){
        super(str);

        // Differentiate between buttons that will cause the door to stick vs.
        // causing the elevator to stick.
        this.elevatorID = elevatorID;
        if (type.equals("StickDoor")) {
            this.type = "Door";
        } else if (type.equals("StickElevator")) {
            this.type = "Elevator";
        } else {
            System.out.println("INVALID");
        }

        // Lambda function to assign an on-click action for this button.
        this.setOnAction((event) -> {
            String text = ((Button)event.getSource()).getText();
            if (this.type.equals("Door")){
                sendDoorStick(elevatorID, waitTime );
                System.out.println("StickDoor Pressed! Assigned to Elevator " + this.elevatorID);
            }

            else if (this.type.equals("Elevator")){
                sendElevatorStick(elevatorID, waitTime);
                System.out.println("Stick Elevator Pressed! Assigned to Elevator " + this.elevatorID);
            }
        }); // end lambda
    }

    /**
     * Return an instance of an ErrorButton.
     * This is used instead of the constructor because the first line of a
     * Java constructor needs to be super(), but for these buttons we first need
     * to determine what label to pass to the parent class.
     * @param elevatorID    The ID of the elevator to associate this with.
     * @param str           The label for the button.
     * @param type          The type of button, either "Door" or "Elevator".
     * @return              An instance of ErrorButtons
     */
    public static ErrorButtons create(int elevatorID, String str, String type){
        if (str.equals("StickDoor")){
            return new ErrorButtons(elevatorID, "StickDoor", type);
        }
        else if (str.equals("StickElevator")){
            return new ErrorButtons(elevatorID, "StickElevator", type);
        }
        else return null;
    }

//    private void sendErrorClick(int floornumber){
//    StuckMessage message = new StuckMessage(elevatorID);
//    RabbitSender sender = new RabbitSender(errorQueue, message);
//    new Thread(sender).start();
//    }

    /**
     * Send a message to associated elevator, instructing it to stick its doors.
     * @param elevatorID    The ID of the elevator.
     * @param time          How long the elevator should keep its doors stuck.
     */
    private void sendDoorStick(int elevatorID, int time){
        DoorStickMessage message = new DoorStickMessage(elevatorID, 2 * waitTime + 1 );
        RabbitSender sender = new RabbitSender(errorQueue, message);
        new Thread(sender).start();
    }

    /**
     * Send a message to the associated elevator, instructing it to stick itself.
     * @param elevatorID    The ID of the elevator.
     * @param time          How long the elevator should stick.
     */
    private void sendElevatorStick(int elevatorID, int time){
        ElevatorStickMessage message = new ElevatorStickMessage(elevatorID, waitTime);
        RabbitSender sender = new RabbitSender(errorQueue, message);
        new Thread(sender).start();
    }
}
