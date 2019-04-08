package com.sysc3303;

import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.ElevatorClickSimulationMessage;
import com.sysc3303.communication.RabbitSender;

import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * ElevatorButton represents a button on the inside of an elevator. Pressing one
 * will make a request for that elevator to travel to a certain floor.
 */
public class ElevatorButton extends Button {
	
	int floorNum;
	int elevatorID;
	Stage parentStage;
	
	public ElevatorButton(String label, int floor, int elevator, Stage stage) {
		super(label);
		this.floorNum = floor;
		this.elevatorID = elevator;
		this.parentStage = stage;

		// Lambda function to assign an on-click action for this button.
		this.setOnAction((event) -> {
            sendElevatorClick(elevatorID, floorNum);
            System.out.println("Pressed button " + floorNum + " in elevator " + elevatorID);
            parentStage.close();
        });
	}

    /**
     * Send a request message to the scheduler using RabbitMQ.
     * @param elevatorID    The ID of the parent elevator.
     * @param floorNum      The target floor.
     */
	private void sendElevatorClick(int elevatorID, int floorNum) {
		System.out.println("Sending click");
		ElevatorClickSimulationMessage message = new ElevatorClickSimulationMessage(floorNum, elevatorID);
		String elevatorQueueName = ConfigProperties.getInstance().getProperty("elevatorQueueName");
		RabbitSender sender = new RabbitSender(elevatorQueueName, message);
		new Thread(sender).start();
		}
	
}
