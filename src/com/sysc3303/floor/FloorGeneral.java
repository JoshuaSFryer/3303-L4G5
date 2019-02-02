//Just to make life little easier, it's gonna get better at next iteration

package com.sysc3303.floor;

public class FloorGeneral {
	
	private int floorNum;
	private FloorButton buttons;
	private FloorLamp lamps;
	int passengerIsOnFloor = 1;
	
	/*
	 * methodFloorArrival deals with information coming from Scheduler Sub-System
	 */
	public FloorGeneral(int floorNum) {
		this.floorNum = floorNum;
		this.buttons = new FloorButton();
		this.lamps = new FloorLamp();
	}
	
	public FloorButton getButtons() {
		return this.buttons;
	}
	
	public FloorLamp getLamps() {
		return this.lamps;
	}
}
