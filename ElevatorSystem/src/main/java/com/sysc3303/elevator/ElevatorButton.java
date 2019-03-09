package com.sysc3303.elevator;

/**
 * Represents a numbered button within an elevator that can be pressed to
 * request a stop at the corresponding floor. 
 * @author Joshua Fryer
 *
 */
public class ElevatorButton {
	
	private int id;
	@SuppressWarnings("unused")
	private boolean illuminated;
	private Elevator parent;
	
	/**
	 * Class constructor.
	 * @param parent	The Elevator this button is part of.
	 * @param id		The number of the button, corresponding to the floor
	 * 					it targets.
	 */
	public ElevatorButton(Elevator parent, int id) {
		this.id = id;
		this.illuminated = false;
		this.parent = parent;
	}
	
	/**
	 * Acknowledges that this button was pressed, and turn on the button's light.
	 */
	public void press() {
		System.out.println("Elevator " +parent.elevatorID+": Button " + this.id + " pressed");
		turnOn();
	}
	
	/**
	 * Turns the button's light on. Call this when the button has been pressed.
	 */
	private void turnOn() {
		System.out.println("Elevator " +parent.elevatorID+": Button " + this.id + "'s light is on.");
		this.illuminated = true;
	}

	/**
	 * Turn this button's light off. Call this when the elevator arrives at the
	 * floor this button represents, to clear it.
	 */
	public void turnOff() {
		System.out.println("Elevator "+parent.elevatorID+": Button " + this.id + "'s light is off.");
		this.illuminated = false;
	}
}
