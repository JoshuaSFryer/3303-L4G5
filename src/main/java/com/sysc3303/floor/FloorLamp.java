package com.sysc3303.floor;

import com.sysc3303.commons.Direction;

/**
 * FloorLamp class turns on two lamps according to the direction for the elevator
 */

public class FloorLamp {
	
	private boolean upLamp;
	private boolean downLamp;
	
	// Constructor
	FloorLamp() {
		this.upLamp = false;
		this.downLamp = false;
	}
	
	/**
	 * method getLampStatus returns the current Status of the lamps
	 */
	public String getLampStatus() {
		return "UP-Lamp = " + upLamp + "  DOWN-Lamp = " + downLamp + "\n";
	}
	
	/**
	 * This method turns the UP Lamps ON
	 */
	public void turnUpLampON() {
		upLamp = true;
		System.out.println("Up Lamp is =" + upLamp);
	}
	
	/**
	 * This Method turns the UP Lamp OFF
	 */
	
	public void turnUpLampOFF() {
		upLamp = false;
		System.out.println("Up Lamp is =" + upLamp);
	}
	
	/**
	 * This Method turns the Down Lamp ON
	 */
	
	public void turnDownLampON() {
		downLamp = true;
		System.out.println("Down Lamp is =" + downLamp);
	}
	
	
	/**
	 * Turns the Down Lamp OFF
	 */
	public void turnDownLampOFF() {
		downLamp = false;
		System.out.println("Down Lamp is =" + downLamp);
	}
	
	public void turnBothLampON() {
		upLamp = true;
		downLamp = true;
	}
	
	public void turnBothLampOFF() {
		upLamp = false;
		downLamp = false;
	}
	
	/**
	 * This method compares the direction of the elevator and turns on the lamps accordingly
	 */
	
	public void flashLamp(Direction direction) {
		if (Direction.UP == direction){
			turnUpLampON();
		}
		
		else if (Direction.DOWN == direction) {
			turnDownLampON();
		}
	}


	public boolean isUpLamp() {
		return upLamp;
	}


	public boolean isDownLamp() {
		return downLamp;
	}
}
