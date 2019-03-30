package com.sysc3303;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.communication.RabbitSender;
import javafx.scene.control.Button;

import static com.sysc3303.commons.Direction.UP;
import static com.sysc3303.commons.Direction.DOWN;
public class CustomButton extends Button {
    int floorNumber;
    Direction dir;
    Main context;
    static String floorQueueName = ConfigProperties.getInstance().getProperty("floorQueueName");

     CustomButton(int floorNumber, Direction dir, String str, Main parent){

         super(str);

        this.floorNumber = floorNumber;
        this.dir = dir;
        this.context = parent;

        this.setOnAction((event) -> {
            sendFloorClick(floorNumber, this.dir);
            System.out.println("BUtton pressed!");
        });

    }
    //This method creates the 
    public static CustomButton create(int floorNumber, Direction dir, Main parent){

        if (dir==UP){
            return new CustomButton(floorNumber, dir, "UP", parent);

        }
        else if (dir == DOWN){
            return new CustomButton(floorNumber, dir, "DOWN", parent);
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
         // TODO: GET RID OF THIS SHIT
        int elev;
        if (direction == Direction.UP) {
            elev = 0;
        } else {
            elev = 1;
        }
        this.context.moveElevator(elev, floorNumber);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}
