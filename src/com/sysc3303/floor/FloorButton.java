package com.sysc3303.floor;

public class FloorButton {
	private boolean up   = false;
	private boolean down = false;
	
	public void pressUp() {
		up = true;
	}
	
	public void pressDown() {
		down = true;
	}
	
	public void disableUp() {
		up = false;
	}
	
	public void disableDown() {
		down = false;
	}
	
	public boolean isUpButtonEnabled() {
		return up;
	}
	
	public boolean isDownButtonEnabled() {
		return down;
	}
	
	/*
	 * isTopFloor returns true if the Elevator is in the Top Floor
	 * 
	 * may be we can take floor number as a parameter here and compare it
	 */
	
	public boolean isTopFloor() {
		
		up = false;
		return up;
	}
	
	/*
	 * isGroundFloor returns true if the Elevator is at the ground level and cannot go further down
	 * 
	 * may be we can take floor number as a parameter here and compare it
	 */
	
	public boolean isGroundFloor() {
		
		down = false;
		return up;
	}
}
