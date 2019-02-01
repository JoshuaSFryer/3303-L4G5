package com.sysc3303.elevator;

public class ElevatorLamp {
	private boolean on;
	
	public ElevatorLamp() {
		this.on = false;
	}
	
	public void setState(boolean newState) {
		this.on = newState;
	}
	
}
