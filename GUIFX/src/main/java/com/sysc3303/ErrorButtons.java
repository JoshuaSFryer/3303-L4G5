package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;

import java.awt.*;

import com.sysc3303.communication.*;
import javafx.scene.control.Button;

public class ErrorButtons extends Button {
    int elevatorID;
    String type;
    static String errorQueue = ConfigProperties.getInstance().getProperty("schedulerQueueName");
    int waitTime = Integer.parseInt(ConfigProperties.getInstance().getProperty("timeBetweenFloors"));

    ErrorButtons (int elevatorID, String str, String type){
        super(str);

        this.elevatorID = elevatorID;
        if (type.equals("StickDoor")) {
            this.type = "Door";
        } else if (type.equals("StickElevator")) {
            this.type = "Elevator";
        } else {
            System.out.println("INVALID");
        }

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



        });
        //System.out.println("Elevator Id" + );
    }

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

    private void sendDoorStick(int elevatorID, int time){

        DoorStickMessage message = new DoorStickMessage(elevatorID, 2 * waitTime + 1 );
        RabbitSender sender = new RabbitSender(errorQueue, message);
        new Thread(sender).start();
    }

    private void sendElevatorStick(int elevatorID, int time){


        ElevatorStickMessage message = new ElevatorStickMessage(elevatorID, waitTime);
        RabbitSender sender = new RabbitSender(errorQueue, message);
        new Thread(sender).start();

    }
}
