package com.sysc3303.gui;

import com.sysc3303.communication.GUIElevatorMoveMessage;
import com.sysc3303.communication.GUIFloorMessage;

public interface UserInterface {

    /**
     * Return a copy of this. Interfaces should follow the Singleton pattern.
     */
    //public UserInterface getInstance();

    /**
     * Respond to an ElevatorMoveMessage, which indicates that one of the
     * elevators have moved or opened/closed doors.
     * The UI should reflect this by updating the elevator's location, indicate
     * the direction of its movement (including idle), and the state of its doors.
     * @param msg   The message passed on from the MessageHandler.
     */
    public void moveElevator(GUIElevatorMoveMessage msg);


    /**
     * Respond to a FloorMessage, which indicates that one of the up/down
     * buttons on one of the floors has been pressed.
     * The UI should reflect this by indicating which of the floor's buttons
     * have been pressed/are "lit up".
     * @param msg   The message passed on from the MessageHandler.
     */
    public void pressFloorButton(GUIFloorMessage msg);


    /**
     * CURRENTLY UNUSED
     * Respond to a press on one of the buttons inside an elevator.
     * This is an interactive method, requiring input into the UI.
     * The UI should reflect that the button has been pressed, create a new
     * GUIElevatorCarMessage and send a request to the Scheduler subsystem via
     * the MessageHandler, notifying it that a "passenger" has requested a floor.
     * @param floor The floor number of the pressed button.
     */
    public void pressElevatorButton(int floor);

}
