package com.sysc3303.elevator;

public class ElevatorButton {
	
	private int id;
	private Elevator parent;
	private boolean illuminated;
	
	public ElevatorButton(Elevator parent, int id) {
		this.id = id;
	}
	
	public void press() {
		// Have the elevator call method to send to scheduler, and turn on light.
		System.out.println("Button " + this.id + " pressed");
		setLight(true);
	}
	
	private void setLight(boolean state) {
		this.illuminated = state;
	}
}
