package com.sysc3303.elevator;

//import java.math.*;
import java.math.BigDecimal;

public class FloorSensor {
	// Height of each floor, in meters
	public static final double FLOORHEIGHT = 5.0;
	
	private Elevator parent;
	//private Thread movementThread;
	
	public FloorSensor(Elevator parent) {
		this.parent = parent;
		//this.movementThread = null;
	}
	
	/**
	 * Get the floor number that the elevator is currently at.
	 * If the elevator is between floors, return -1.
	 * @return 	The current floor number, if at a floor. -1, if it is between
	 * 			floors.
	 */
	public int getFloor() {
		// Get the elevator's height as a BigDecimal.
		// Use BigDecimal for safe floating-point division.
		BigDecimal height = new BigDecimal(parent.getCurrentHeight());
		
		// Divide the current height by the height of a single floor.
		// Store the value and remainder in an BigDecimal array.
		BigDecimal[] result = height.divideAndRemainder(
							  new BigDecimal(FLOORHEIGHT));
		
		// If the remainder is zero, then return the current floor number
		// as an integer. Otherwise, return -1.
		if(result[1].compareTo(BigDecimal.ZERO) == 0) {
			return result[0].intValue();
		} else {
			return -1;
		}
		
	}
	
	public boolean isAtFloor() {
		return (getFloor() >= 0);
	}
	
	public boolean hasArrived(int target) {
		return (getFloor() == target);
	}
	/*
	public void attachThread(Thread t) {
		this.movementThread = t;
	}
	
	public void detachThread() {
		this.movementThread = null;
	}
	*/
}
