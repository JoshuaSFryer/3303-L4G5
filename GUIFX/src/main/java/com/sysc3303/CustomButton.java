package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.communication.RabbitSender;
import javafx.scene.control.Button;

import static com.sysc3303.commons.Direction.UP;
import static com.sysc3303.commons.Direction.DOWN;

/**
 * CustomButton is used as the up/down buttons for each floor. Pressing one of
 * them will send a request to the Scheduler for the floor they are associated
 * with, in their given direction.
 */
public class CustomButton extends Button  {
    int floorNumber;
    Direction dir;
    static String floorQueueName = ConfigProperties.getInstance().getProperty("floorQueueName");

    private GUIMessageHandler messageHandler;

    CustomButton(int floorNumber, Direction dir, String str, GUIMessageHandler messageHandler){
         super(str);
         this.messageHandler = messageHandler;

        this.floorNumber = floorNumber;
        this.dir = dir;

        // Lambda function to assign an on-click action for this button.
        this.setOnAction((event) -> {
            this.getStyleClass().removeAll("button");
            sendFloorClick(floorNumber, this.dir);
            System.out.println("Floor Button pressed!");
            this.getStyleClass().add("buttonChanged");
        });

    }

    /**
     * Get an instance of a CustomButton.
     * This is used instead of the constructor because the first line of a
     * Java constructor needs to be super(), but for these buttons we first need
     * to determine what label to pass to the parent class.
     * @param floorNumber   The floor this button is associated with.
     * @param dir           The direction this button is associated with.
     * @return              An instance of CustomButton.
     */
    public static CustomButton create(int floorNumber, Direction dir, GUIMessageHandler messageHandler){

        if (dir==UP){
            return new CustomButton(floorNumber, dir, "UP", messageHandler);

        }
        else if (dir == DOWN){
            return new CustomButton(floorNumber, dir, "DOWN", messageHandler);
        }

        else { // invalid case, but should never happen.
            System.out.println("Invalid direction provided when creating button");
            return null;
        }

    }

    /**
     * Send a message to the floor system via RabbitMQ, simulating a request in
     * a given direction from a given floor.
     * @param floorNumber   The floor the button is from.
     * @param direction     The direction associated with the button.
     */
    private void sendFloorClick(int floorNumber, Direction direction){
         messageHandler.sendFloorClick(floorNumber, direction);
    }

    /**
     * Get the floor number associated with this button.
     * @return  The floor number.
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Set this button's floor number.
     * @param floorNumber   The new floor number.
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * Unclick this button, restoring its appearance to its original state.
     */
    public void unClickButton(){
         this.getStyleClass().removeAll("buttonChanged");
         System.out.println("Button Unclicked");
         this.getStyleClass().add("button");
    }

    /**
     * Click this button and change its appearance to indicate that is has been
     * clicked.
     */
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
