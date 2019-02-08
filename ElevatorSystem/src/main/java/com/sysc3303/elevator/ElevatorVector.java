package com.sysc3303.elevator;
import com.sysc3303.commons.Direction;

@SuppressWarnings("serial")
public class ElevatorVector implements java.io.Serializable {
	
	public final int currentFloor;
	public final Direction currentDirection;
	public final int targetFloor;
	
	public ElevatorVector(int floor, Direction dir, int targetFloor) {
		this.currentFloor = floor;
		this.currentDirection = dir;
		this.targetFloor = targetFloor;
	}

	public String toString() {
		return "currentFloor: " + currentFloor + 
			   " currentDirection:" + currentDirection + 
			   " targetFloor: " + targetFloor;
	}
}