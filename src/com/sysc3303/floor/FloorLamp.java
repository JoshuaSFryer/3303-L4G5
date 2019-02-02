package com.sysc3303.floor;

import com.sun.javafx.scene.traversal.Direction;

public class FloorLamp {
	
	private boolean upLamp;
	private boolean downLamp;
	
	FloorLamp() {
		this.upLamp = false;
		this.downLamp = false;
	}
	

	public String getLampStatus() {
		return "UP-Lamp = " + upLamp + "  DOWN-Lamp = " + downLamp + "\n";
	}
	
	public void turnUpLampON() {
		upLamp = true;
	}
	
	public void turnUpLampOFF() {
		upLamp = false;
	}
	
	public void turnDownLampON() {
		downLamp = true;
	}
	
	public void turnDownLampOFF() {
		downLamp = false;
	}
	
	public void turnBothLampON() {
		upLamp = true;
		downLamp = true;
	}
	
	public void turnBothLampOFF() {
		upLamp = false;
		downLamp = false;
	}
	
	
	
	public void flashLamp(Direction direction) {
		if (Direction.UP == direction){
			turnUpLampON();
		}
		
		else if (Direction.DOWN == direction) {
			turnDownLampON();
		}
	}
}
