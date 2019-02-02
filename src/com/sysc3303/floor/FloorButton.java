package com.sysc3303.floor;

public class FloorButton {
//	private boolean up   = false;
//	private boolean down = false;
	
	//Button Creates an Instance of Lamp every time
	FloorLamp lamp = new FloorLamp();
	
	
	//If upButtonPressed
	public void upButtonPressed() {
		lamp.flashLamp("up");
	}
	
	//If DownButtonPressed
	public void downButtonPressed() {
		lamp.flashLamp("down");
	}
	
	public static void main(String[] args) {
		
		FloorButton buttonTest = new FloorButton();
		
		buttonTest.downButtonPressed();
		buttonTest.upButtonPressed();
	}
}
