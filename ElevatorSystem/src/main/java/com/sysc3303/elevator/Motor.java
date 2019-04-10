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
	private boolean isStuck = false;
	
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
		if (!isStuck) {
			parent.setCurrentHeight(parent.getCurrentHeight() + INCREMENT);
		} else {
			System.out.println("Motor instructed to move, but cannot because it is stuck");
		}
	}
	
	/**
	 * Move the elevator down by one unit.
	 */
	public void moveDown() {
		if (!isStuck) {
			parent.setCurrentHeight(parent.getCurrentHeight() - INCREMENT);
		} else {
			System.out.println("Motor instructed to move, but cannot because it is stuck");
		}
	}

	/**
	 * Cause this motor to stick, making it unable to move.
	 */
	public void stick() {
		System.out.println("Sticking motor");
		isStuck = true;
	}

    /**
     * Cause this motor to unstick, allowing it to move again.
     */
	public void unstick() {
		System.out.println("Unsticking motor");
		isStuck = false;
	}
}
