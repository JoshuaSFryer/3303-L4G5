package com.sysc3303.elevator;

import com.sysc3303.commons.Direction;

public class ElevatorLamp {
	private int currentFloor;
	
	public ElevatorLamp() {
		this.setCurrentFloor(1, Direction.IDLE); //TODO: Demagicify
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor, Direction dir) {
		this.currentFloor = currentFloor;
		System.out.println("Lamp says: Floor " + currentFloor + ", moving "
							+ dir.name());
	}

}
