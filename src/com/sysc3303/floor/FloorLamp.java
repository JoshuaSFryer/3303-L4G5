package com.sysc3303.floor;

public class FloorLamp {
	
	private String up = "up";
	private String down = "down";
	private String elevatorArrived = "elevatorArrived";
		
	
	private String upLamp;
	private String downLamp;
	
	FloorLamp() {
		upLamp = "OFF";
		downLamp = "OFF";
	}
	

	public String getLampStatus() {
		return "UP-Lamp = " + upLamp + "  DOWN-Lamp = " + downLamp + "\n";
	}
	

	
	public void turnUpLampON() {
		upLamp = "ON";
		downLamp = "OFF";
	}
	
	public void turnDownLampON() {
		upLamp = "OFF";
		downLamp = "ON";
	}
	
	public void turnBothLampON() {
		upLamp = "ON";
		downLamp = "ON";
	}
	
	public void turnBothLampOFF() {
		upLamp = "OFF";
		downLamp = "OFF";
	}
	
	
	
	public void flashLamp(String dir) {
		if (dir.equals(up)){
			turnUpLampON();
		}
		
		else if (dir.equals(down)) {
			turnDownLampON();
		}
		
		else if (dir.equals(elevatorArrived)) {
			turnBothLampOFF();
		}
	}
	
	

}
