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
}
