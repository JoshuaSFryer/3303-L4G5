package com.sysc3303.elevator;

/**
 * Represents a numbered button within an elevator that can be pressed to
 * request a stop at the corresponding floor. 
 * @author Joshua Fryer
 *
 */
public class ElevatorButton {
	
	private int id;
	//private Elevator parent;
	@SuppressWarnings("unused")
	private boolean illuminated;
	
	/**
	 * Class constructor.
	 * @param parent	The Elevator this button is part of.
	 * @param id		The number of the button, corresponding to the floor
	 * 					it targets.
	 */
	public ElevatorButton(Elevator parent, int id) {
		this.id = id;
		this.illuminated = false;
	}
	
	/**
	 * Acknowledges that this button was pressed, and turn on the button's light.
	 */
	public void press() {
		System.out.println("Button " + this.id + " pressed");
		setLight(true);
	}
	
	/**
	 * Turns the button's light on or off
	 * @param state	True to turn on the light, false to turn it off.
	 */
	private void setLight(boolean state) {
		System.out.println("Button " + this.id + "'s light is on.");
		this.illuminated = state;
	}
}
