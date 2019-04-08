package com.sysc3303;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * GUIElevator is a specialized JavaFX rectangle that stores extra data.
 * It is used to represent an elevator in the system.
 */
public class GUIElevator extends Rectangle {
    int elevatorID;
    int currentFloor;

    GUIElevator(int elevatorID, int currentFloor){
        super();
        this.elevatorID = elevatorID;
        this.currentFloor = currentFloor;
        this.setWidth(40);
        this.setHeight(40);
        this.setFill(Color.YELLOW);
    }
}
