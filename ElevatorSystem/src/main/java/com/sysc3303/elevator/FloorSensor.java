package com.sysc3303.elevator;


/**
 * Represents a sensor in the elevator that determines whether the elevator has
 * arrived at a floor.
 * @author Joshua Fryer
 *
 */
public class FloorSensor implements Runnable {
	// Height of each floor, in meters
	public static final int FLOORHEIGHT = 5;
	// How long to sleep, in milliseconds.
	public static final int SLEEPTIME = 500;
	
	private Elevator parent;
	
	public FloorSensor(Elevator parent) {
		this.parent = parent;
	}
	
	/**
	 * Get the floor number that the elevator is currently at.
	 * If the elevator is between floors, return -1.
	 * @return 	The current floor number, if at a floor. -1, if it is between
	 * 			floors.
	 */
	public int getFloor() {
		
		int height = parent.getCurrentHeight();
		// If the elevator's height is a whole multiple of FLOORHEIGHT, it has
		// arrived at a floor.
		if(height % FLOORHEIGHT == 0) {
			// If the elevator's height is a whole multiple of FLOORHEIGHT, it has
			// arrived at a floor.
			
			// Using bare integer division should be perfectly acceptable; since
			// the modulus is 0, they must by definition be divisible.
			return height/FLOORHEIGHT;
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

	public void run() {
		while(true) {
			try {
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				System.out.println("Floor sensor was interrupted while sleeping");
			}

			if(isAtFloor()) {
				// Interrupt the movement handler.
				parent.getMovementHandler().interrupt();
			}
		}
	}
}
