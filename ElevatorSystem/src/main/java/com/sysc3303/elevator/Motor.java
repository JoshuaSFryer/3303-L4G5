package com.sysc3303.elevator;

/**
 * Represents the motor winch that raises or lowers an elevator.
 * @author Joshua Fryer
 *
 */
public class Motor {
	// How far a single call of moveUp() or moveDown() will move the elevator.
	private final int INCREMENT = 1; // 1 cm
	private Elevator parent;
	
	/**
	 * Class constructor.
	 * @param parent The elevator this motor is part of.
	 */
	public Motor(Elevator parent) {
		this.parent = parent;
	}
	
	/**
	 * Move the elevator up by one unit.
	 */
	public void moveUp() {
		parent.setCurrentHeight(parent.getCurrentHeight() + INCREMENT);
	}
	
	/**
	 * Move the elevator down by one unit.
	 */
	public void moveDown() {
		parent.setCurrentHeight(parent.getCurrentHeight() - INCREMENT);
	}
	/*
	public void stop() {
		
	}
	*/
}
