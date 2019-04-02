package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;

import java.awt.*;

import com.sysc3303.communication.RabbitSender;
import com.sysc3303.communication.StuckMessage;
import javafx.scene.control.Button;

public class ErrorButtons extends Button {
    int elevatorID;
    static String errorQueue = ConfigProperties.getInstance().getProperty("schedulerQueueName");

    ErrorButtons (int elevatorID, String str){
        super(str);

        this.elevatorID = elevatorID;

        this.setOnAction((event) -> {
            sendErrorClick(elevatorID);
            System.out.println("Error Button Pressed! ");
        });
        //System.out.println("Elevator Id" + );
    }

    public static ErrorButtons create(int elevatorID, String str){
        if (str.equals("StickDoor")){
            return new ErrorButtons(elevatorID, "StickDoor");
        }
        else if (str.equals("StickElevator")){
            return new ErrorButtons(elevatorID, "StickElevator");
        }
        else return null;
    }

    private void sendErrorClick(int floornumber){
    StuckMessage message = new StuckMessage(elevatorID);
    RabbitSender sender = new RabbitSender(errorQueue, message);
    new Thread(sender).start();

    }
}
