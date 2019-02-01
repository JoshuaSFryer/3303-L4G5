package com.sysc3303.elevator;

public class Door {
	
	// Open = true, closed = false.
	@SuppressWarnings("unused")
	private boolean doorState;
	
	public Door() {
		this.doorState = false;
	}
	
	public void openDoors() {
		this.doorState = true;
	}
	
	public void closeDoors() {
		this.doorState = false;
	}

}
