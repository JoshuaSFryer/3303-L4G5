package com.sysc3303;

import com.sysc3303.communication.FloorClickSimulationMessage;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.ElevatorClickSimulationMessage;
import com.sysc3303.communication.RabbitSender;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ElevatorButton extends Button {
	
	int floorNum;
	int elevatorID;
	Stage parentStage;
	
	public ElevatorButton(String label, int floor, int elevator, Stage stage) {
		super(label);
		this.floorNum = floor;
		this.elevatorID = elevator;
		this.parentStage = stage;
		
		this.setOnAction((event) -> {
            sendElevatorClick(elevatorID, floorNum);
            System.out.println("Pressed button " + floorNum + " in elevator " + elevatorID);
            parentStage.close();
        });
		System.out.println("ACtion set");
	}
	
	private void sendElevatorClick(int elevatorID, int floorNum) {
		System.out.println("Sending click");
		ElevatorClickSimulationMessage message = new ElevatorClickSimulationMessage(floorNum, elevatorID);
		String elevatorQueueName = ConfigProperties.getInstance().getProperty("elevatorQueueName");
		RabbitSender sender = new RabbitSender(elevatorQueueName, message);
		new Thread(sender).start();
		}
	
}
