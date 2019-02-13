package com.sysc3303.scheduler;

import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;

public abstract class RequestHandler {
	Request                 request;
	SchedulerMessageHandler schedulerMessageHandler;
	Message                 message;
	TargetFloorDecider      targetFloorDecider = new TargetFloorDecider();

	protected void generateAndSendGoToFloorMessage() {
		int[] targetFloorsFromFloorButtonMessages    = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request);
		int[] targetFloorsFromElevatorButtonMessages = targetFloorDecider.selectFloorFromAllElevatorsElevatorButtonMessage(request);
		int   numberOfElevator                       = request.getNumberOfElevator();
		
		for(int i = 0; i < numberOfElevator; i++) {
			int currentFloor = request.getElevatorVector(i).currentFloor;
			int targetFloor  = targetFloorDecider.getNearestFloor(targetFloorsFromFloorButtonMessages[i], targetFloorsFromElevatorButtonMessages[i], currentFloor);
			
			if(targetFloor != -1) {
				GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor, i);
				
				System.out.println("Sending go to floor to elevator");
				System.out.println(goToFloorMessage.toString());
				schedulerMessageHandler.sendGoToFloor(goToFloorMessage);
			}
		}
	}
}
