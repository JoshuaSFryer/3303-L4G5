/*
 * FloorGeneral is the General Floor Class. When necessary, instances of this class
 * is created to represent the number of floors
 */

package com.sysc3303.floor;

public class FloorGeneral {
	
	private int floorNum;
	private FloorButton buttons;
	private FloorLamp lamps;
	int passengerIsOnFloor = 1;
	
	//Constructor
	public FloorGeneral(int floorNum) {
		this.floorNum = floorNum;
		this.buttons = new FloorButton();
		this.lamps = new FloorLamp();
	}
	
	/**
	 * Regular getter Method
	 */
	public FloorButton getButtons() {
		return this.buttons;
	}
	
	/**
	 * Regular Setter Method
	 */
	
	public FloorLamp getLamps() {
		return this.lamps;
	}

	public int getFloorNum() {
		return this.floorNum;
	}
}
