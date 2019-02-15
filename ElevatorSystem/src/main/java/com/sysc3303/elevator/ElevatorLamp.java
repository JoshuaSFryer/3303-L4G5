package com.sysc3303.elevator;

import com.sysc3303.commons.Direction;

/**
 * ElevatorLamp represents the display panel inside an elevator that indicates
 * what floor it is currently at, and in which direction it is travelling.
 */
public class ElevatorLamp {
	private int 		currentFloor;
	private Direction 	currentDirection;
	
	public ElevatorLamp() {
		this.setCurrentFloor(1, Direction.IDLE); //TODO: Demagicify
	}

	/**
	 * Set the floor number and direction for the lamp to display.
	 * @see Direction
	 * @param currentFloor 	The floor number to display.
	 * @param dir			The direction to display.
	 */
	public void setCurrentFloor(int currentFloor, Direction dir) {
		this.currentFloor = currentFloor;
		this.currentDirection = dir;
		System.out.println("Lamp says: Floor " + currentFloor + ", moving "
							+ currentDirection.name());
	}

}
