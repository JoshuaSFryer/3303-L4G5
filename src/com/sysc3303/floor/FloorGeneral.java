//Just to make life little easier, it's gonna get better at next iteration

package com.sysc3303.floor;

public class FloorGeneral {
	
	FloorButton buttons = new FloorButton();
	FloorLamp lamps = new FloorLamp();
	
	String direction;
	String lamp;
	
	FloorGeneral (){
		direction = "NONE";
		lamp = "OFF";
	}
	
	
	public void setDirectionForLamp(String dir) {
		
		lamps.flashLamp(dir);
		System.out.println(lamps.getLampStatus());
	}
	
	public void setButton(String dir) {
		buttons.setButton(dir);
		System.out.println(buttons.getButtonStatus());
	}
	
	public String getStatusofLampandButton() {
		
		return lamps.getLampStatus() + buttons.getButtonStatus();
	}
	
//	public void pressedButton(String dir) {
//		buttons.pressedButton();
//	}

}
