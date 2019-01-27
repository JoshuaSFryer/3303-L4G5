package com.sysc3303.floor;

public class FloorLamp {
	private boolean up   = false;
	private boolean down = false;
	
	public void lightUp() {
		up = true;
	}
	
	public void lightDown() {
		down = true;
	}
	
	public void disableLightUp() {
		up = false;
	}
	
	public void disableLightDown() {
		down = false;
	}
	
	public boolean isUpLightEnabled() {
		return up;
	}
	
	public boolean isDownLightEnabled() {
		return down;
	}
}
