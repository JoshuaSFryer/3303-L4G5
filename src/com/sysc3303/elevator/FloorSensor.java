package com.sysc3303.elevator;

//import java.math.*;
import java.math.BigDecimal;

/**
 * Represents a sensor in the elevator that determines whether the elevator has
 * arrived at a floor.
 * @author Joshua Fryer
 *
 */
public class FloorSensor {
	// Height of each floor, in meters
	public static final int FLOORHEIGHT = 5;
	
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
		// TODO: Refactor this to account for height now being an integer.
		
		/*
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
		*/
		int height = parent.getCurrentHeight();
		// If the elevator's height is a whole multiple of FLOORHEIGHT, it has
		// arrived at a floor.
		if(height % FLOORHEIGHT == 0) {
			// If the elevator's height is a whole multiple of FLOORHEIGHT, it has
			// arrived at a floor.
			
			// Use floorDiv() to get a rounded value. This shouldn't be
			// necessary if the modulus is zero, but is being used out of 
			// paranoia.
			return Math.floorDiv(height, FLOORHEIGHT);
		} else { 
			// The elevator is somewhere between two floors.
			return -1;
		}
		
	}
	
	/**
	 * Check whether the elevator is at a floor, not partway between them.
	 * @return True if the elevator is at a floor, false otherwise.
	 */
	public boolean isAtFloor() {
		return (getFloor() >= 0);
	}
	
	/**
	 * Check whether the elevator has arrived at a given floor.
	 * @param target	The floor that should be reached.
	 * @return 	True if the elevator has reached the target floor, false
	 * 			otherwise.
	 */
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
