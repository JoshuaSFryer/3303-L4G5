package com.sysc3303.floor;

public class FloorLamp {
	
	private String up;
	private String down;
	private String elevatorArrived;
	
	/*
	 * The Simulator System will send integers for the direction requested by the passenger.
	 * The floor Class should compare those integers with Strings "up", "down" or "elevatorArrived".
	 *
	 * Method flashLamp prints out statement based on which direction-button was pressed by the 
	 * passenger on the floor. This information is sent from the Input Simulator. 
	 * 
	 * @param direction, direction requested by the passenger
	 */
	public void flashLamp(String direction) {
		if (direction.equals(up)) {
			//UP LAMP is ON, lampUP = ON
			System.out.println("Direction Light UP is ON\n");
		}
		
		else if (direction.equals(down)) {
			//DOWN LAMP is ON, lampDOWN = ON
			System.out.println("Direction Light DOWN is ON\n");
		}
		
		//If the Elevator has Arrived on the destination floor, then both lights should be turned OFF
		else if (direction.equals(elevatorArrived)) {
			// Keep both the Lamps turned off All the time
			System.out.println("Direction Lights are OFF\n");
		}
		
	}
	
	/*
	 * We also have another lamp that says if the Elevator has Arrived.
	 * 
	 * Method flashArrivalLamp turns ON if the Elevator has Arrived. Otherwise, it stays OFF
	 */
	
	public void flashArrivalLamp(String arrivalmessage) {
		if (arrivalmessage.equals(elevatorArrived)) {
			System.out.println("ElevatorArrivalLamp turned ON, ElevatorHas Arrived\n");
		}
		
		else {
			System.out.println("Elevator Arrival is OFF\n");
		}
	}
	
//	public void lightDown(boolean ) {
//		down = true;
//		System.out.println("Direction Light DOWN is ON");
//	}
//	
//	public void disableLightUp() {
//		up = false;
//	}
//	
//	public void disableLightDown() {
//		down = false;
//	}
//	
//	public boolean isUpLightEnabled() {
//		return up;
//	}
//	
//	public boolean isDownLightEnabled() {
//		return down;
//	}
}
