package com.sysc3303.elevator;
import com.sysc3303.commons.Direction;

public class ElevatorVector implements java.io.Serializable {
	
	public final int currentFloor;
	public final Direction currentDirection;
	
	public ElevatorVector(int floor, Direction dir) {
		this.currentFloor = floor;
		this.currentDirection = dir;
	}

}
