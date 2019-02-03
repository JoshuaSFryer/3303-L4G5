package com.sysc3303.floor;

public class FloorButton {
	
	private boolean upButtonLight;
	private boolean downButtonLight;
	
	FloorButton(){
		this.upButtonLight = false;
		this.downButtonLight = false;
	}

	public String getButtonStatusString() {
		return "Up-Button = " + upButtonLight + "  Down-Button = " + downButtonLight;
	}
	
	public boolean isUpButtonLight() {
		return upButtonLight;
	}

	public void setUpButtonLight(boolean upButtonLight) {
		this.upButtonLight = upButtonLight;
	}

	public boolean isDownButtonLight() {
		return downButtonLight;
	}

	public void setDownButtonLight(boolean downButtonLight) {
		this.downButtonLight = downButtonLight;
	}
}
