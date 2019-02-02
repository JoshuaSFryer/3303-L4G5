package com.sysc3303.floor;

public class FloorButton {
	
	String upButton;
	String downButton;
	
	private String up = "up";
	private String down = "down";
	private String elevatorArrived = "elevatorArrived";
	
	
	FloorButton(){
		upButton = " Not Pressed";
		downButton = " Not Pressed";
	}
	
	

	public String getButtonStatus() {
		return "Up-Button = " + upButton + "  Down-Button = " + downButton;
	}
	
	public void setButton(String button) {
		if (button.equals(up)) {
			upButton = "Pressed";
			downButton = "Not Pressed";
		}
		
		else if (button.equals(down)) {
			downButton = "Pressed";
			upButton = "Not Pressed";
		}
		
		else if (button.equals(elevatorArrived)){
			upButton = "Not Pressed";
			downButton = "Not Pressed";
			
		}
	}
	
	

}
