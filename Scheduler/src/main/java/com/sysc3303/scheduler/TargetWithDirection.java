package com.sysc3303.scheduler;

import com.sysc3303.commons.Direction;

public class TargetWithDirection {
	private final int       targetFloor;
	private final Direction targetDirection;
	
	public TargetWithDirection(int targetFloor, Direction targetDirection) {
		this.targetFloor = targetFloor;
		this.targetDirection = targetDirection;
	}

	public int getTargetFloor() {
		return targetFloor;
	}

	public Direction getTargetDirection() {
		return targetDirection;
	}
	
	public String toString() {
		return "\n TargetFloor: " + targetFloor + 
			   "\n TargetDirection: " + targetDirection;
	}
}
