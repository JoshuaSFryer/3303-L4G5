package com.sysc3303.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
