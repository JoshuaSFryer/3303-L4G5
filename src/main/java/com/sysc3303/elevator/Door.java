package com.sysc3303.elevator;

public class Door {
	
	// Open = true, closed = false.
	@SuppressWarnings("unused")
	private boolean doorState;
	
	public Door() {
		this.doorState = false;
	}
	
	public void openDoors() {
		System.out.println("Opening doors");
		this.doorState = true;
	}
	
	public void closeDoors() {
		System.out.println("Closing doors");
		this.doorState = false;
	}

	public boolean isOpen() {
		return this.doorState;
	}

}
