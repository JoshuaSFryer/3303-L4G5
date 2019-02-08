package com.sysc3303.floor;

/**
 * Class FloorButton deals with the clicks of Button
 */

public class FloorButton {
	
	private boolean upButtonLight;
	private boolean downButtonLight;
	
	//Constructor
	FloorButton(){
		this.upButtonLight = false;
		this.downButtonLight = false;
	}
	
	/**
	 * Getter Method to get the status of the current status of the buttons
	 */

	public String getButtonStatusString() {
		return "Up-Button = " + upButtonLight + "  Down-Button = " + downButtonLight;
	}
	
	
	public boolean isUpButtonLight() {
		return upButtonLight;
	}
	
	/**
	 * Sets the Up Button Light
	 * @param upButtonLight
	 */
	public void setUpButtonLight(boolean upButtonLight) {
		this.upButtonLight = upButtonLight;
		System.out.println("Up Light " + this.upButtonLight);
	}

	public boolean isDownButtonLight() {
		return downButtonLight;
	}
	
	/**
	 * Sets the Down Button Light
	 * @param downButtonLight
	 */

	public void setDownButtonLight(boolean downButtonLight) {
		this.downButtonLight = downButtonLight;
		System.out.println("Down Light " + this.downButtonLight);
	}
}
