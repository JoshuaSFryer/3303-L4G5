package com.sysc3303.scheduler;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.ElevatorStateMessage;

public class ElevatorStateMessageValidator {
	public boolean isValidStateMessage(ElevatorStateMessage message, Request request) {
		int            elevatorId     = message.getElevatorId();
		ElevatorVector elevatorVector = request.getElevatorVector(elevatorId);
		ElevatorVector messageVector  = message.getElevatorVector();
		
		if(messageVector.targetFloor == elevatorVector.targetFloor) {
		
			if(messageVector.currentDirection == Direction.UP) {
				if(elevatorVector.currentFloor > messageVector.currentFloor) {
					return false;
				}
			}
			else if(messageVector.currentDirection == Direction.DOWN) {
				if(elevatorVector.currentFloor < messageVector.currentFloor) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
