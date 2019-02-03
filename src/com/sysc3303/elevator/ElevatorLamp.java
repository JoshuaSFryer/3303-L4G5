package com.sysc3303.elevator;

public class ElevatorLamp {
	private int currentFloor;
	
	public ElevatorLamp() {
		this.setCurrentFloor(0); //TODO: Demagicify
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

}
