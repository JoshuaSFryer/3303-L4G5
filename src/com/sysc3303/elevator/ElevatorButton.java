package com.sysc3303.elevator;

public class ElevatorButton {
	
	private int id;
	//private Elevator parent;
	@SuppressWarnings("unused")
	private boolean illuminated;
	
	public ElevatorButton(Elevator parent, int id) {
		this.id = id;
		this.illuminated = false;
	}
	
	public void press() {
		System.out.println("Button " + this.id + " pressed");
		setLight(true);
	}
	
	private void setLight(boolean state) {
		this.illuminated = state;
	}
}
