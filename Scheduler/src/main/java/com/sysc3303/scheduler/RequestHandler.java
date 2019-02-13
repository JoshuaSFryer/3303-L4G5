package com.sysc3303.scheduler;

import org.apache.log4j.Logger;

import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;

public abstract class RequestHandler {
	protected Request                 request;
	protected SchedulerMessageHandler schedulerMessageHandler;
	protected Message                 message;
	protected TargetFloorDecider      targetFloorDecider = new TargetFloorDecider();
	protected Logger                  log                = Logger.getLogger(SchedulerMessageHandler.class);

	protected void generateAndSendGoToFloorMessage() {
		int[] targetFloorsFromFloorButtonMessages    = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request);
		int[] targetFloorsFromElevatorButtonMessages = targetFloorDecider.selectFloorFromAllElevatorsElevatorButtonMessage(request);
		int   numberOfElevator                       = request.getNumberOfElevator();
		
		for(int i = 0; i < numberOfElevator; i++) {
			int currentFloor = request.getElevatorVector(i).currentFloor;
			int targetFloor  = targetFloorDecider.getNearestFloor(targetFloorsFromFloorButtonMessages[i], targetFloorsFromElevatorButtonMessages[i], currentFloor);
			
			if(targetFloor != -1) {
				ElevatorVector curElevatorVector = request.getElevatorVector(i);
				ElevatorVector elevatorVector    = new ElevatorVector(curElevatorVector.currentFloor, curElevatorVector.currentDirection, targetFloor);
				request.setElevatorVector(elevatorVector, i);
				schedulerMessageHandler.sendGoToFloor(new GoToFloorMessage(targetFloor, i));
			}
		}
	}
}