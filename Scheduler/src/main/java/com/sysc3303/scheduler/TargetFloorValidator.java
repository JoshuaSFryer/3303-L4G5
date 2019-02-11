package com.sysc3303.scheduler;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

public class TargetFloorValidator {

	public boolean validTargetFloor(int targetFloor, ElevatorVector elevatorVector) {
		Direction      currentDirection   = elevatorVector.currentDirection;
		int            currentTargetFloor = elevatorVector.targetFloor;
		int            currentFloor       = elevatorVector.currentFloor;
		
		if(currentFloor == currentTargetFloor || elevatorVector.currentDirection == Direction.IDLE) {
			return true;
		}
		
		System.out.println("Checking target: " + targetFloor);
		
		if(currentDirection == Direction.UP) {
			if(targetFloor < currentTargetFloor && targetFloor > currentFloor) {
				return true;
			}
		}
		else if(currentDirection == Direction.DOWN) {
			if(targetFloor > currentTargetFloor && targetFloor < currentFloor) {
				return true;
			}
		}
		return false;
	}
}
