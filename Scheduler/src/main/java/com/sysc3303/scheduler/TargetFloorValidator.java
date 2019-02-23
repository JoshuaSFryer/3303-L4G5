package com.sysc3303.scheduler;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

/**
 * Validates if target floor is
 * valid floor for elevator
 * @author Yu Yamanaka
 *
 */
public class TargetFloorValidator {

	/**
	 * Checks if targetFloor is valid floor
	 * for elevator considering direction. Should
	 * be used for Floor Button messages
	 * @param targetFloor
	 * @param targetDirection
	 * @param elevatorVector
	 * @return
	 */
	public boolean validTargetFloor(int buttonFloor, Direction buttonDirection, ElevatorVector elevatorVector, Direction targetDirection) {
		Direction      currentDirection   = elevatorVector.currentDirection;
		int            currentTargetFloor = elevatorVector.targetFloor;
		int            currentFloor       = elevatorVector.currentFloor;
		
		if(currentFloor == currentTargetFloor || currentDirection == Direction.IDLE) {
			return true;
		}
		
		if(buttonDirection == Direction.UP && targetDirection == Direction.UP) {
			if(buttonFloor < currentTargetFloor && buttonFloor > currentFloor) {
				return true;
			}
		}
		else if(buttonDirection == Direction.DOWN && targetDirection == Direction.DOWN) {
			if(buttonFloor > currentTargetFloor && buttonFloor < currentFloor) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if targetFloor is valid floor
	 * for elevator. Should be used for
	 * elevator button messages
	 * @param targetFloor
	 * @param elevatorVector
	 * @return
	 */
	public boolean validTargetFloor(int targetFloor, ElevatorVector elevatorVector) {
		Direction      currentDirection   = elevatorVector.currentDirection;
		int            currentTargetFloor = elevatorVector.targetFloor;
		int            currentFloor       = elevatorVector.currentFloor;
		
		if(currentFloor == currentTargetFloor || elevatorVector.currentDirection == Direction.IDLE) {
			return true;
		}
		
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
