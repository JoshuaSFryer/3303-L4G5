package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.communication.RabbitSender;
import javafx.scene.control.Button;

import static com.sysc3303.commons.Direction.UP;
import static com.sysc3303.commons.Direction.DOWN;
public class CustomButton extends Button  {
    int floorNumber;
    Direction dir;
    static String floorQueueName = ConfigProperties.getInstance().getProperty("floorQueueName");

     CustomButton(int floorNumber, Direction dir, String str){

         super(str);

        this.floorNumber = floorNumber;
        this.dir = dir;

        this.setOnAction((event) -> {
            this.getStyleClass().removeAll("button");
            sendFloorClick(floorNumber, this.dir);
            System.out.println("Floor Button pressed!");
            this.getStyleClass().add("buttonChanged");
        });

    }
    //This method creates the 
    public static CustomButton create(int floorNumber, Direction dir){

        if (dir==UP){
            return new CustomButton(floorNumber, dir, "UP");

        }
        else if (dir == DOWN){
            return new CustomButton(floorNumber, dir, "DOWN");
        }

        else { // invalid case
            System.out.println("Invalid direction provided when creating button");
            return null;
        }

    }
    // This method sends messages to RabbitMQ
    private void sendFloorClick(int floorNumber, Direction direction){
         FloorClickSimulationMessage message = new FloorClickSimulationMessage(floorNumber, direction);
         RabbitSender sender = new RabbitSender(floorQueueName, message);
         new Thread(sender).start();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void unClickButton(){
         this.getStyleClass().removeAll("buttonChanged");
        System.out.println("Button Unclicked");
         this.getStyleClass().add("button");
    }

    public void clickButton() {
        this.getStyleClass().removeAll("button");
        this.getStyleClass().add("buttonChanged");
        System.out.println("Button Clicked");
    }

//    @Override
//    public void run() {
//        this.setOnAction((event) -> {
//            sendFloorClick(floorNumber, this.dir);
//            System.out.println("BUtton pressed!");
//        });
//    }
}
