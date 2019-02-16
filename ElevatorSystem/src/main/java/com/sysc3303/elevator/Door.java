package com.sysc3303.elevator;

/**
 * Door represents and elevator's doors. They can be open or closed.
 */
public class Door {
	
	// Open = true, closed = false.
	@SuppressWarnings("unused")
	private boolean doorState;

	/**
	 * Class constructor.
	 */
	public Door() {
		// Doors default to closed.
		this.doorState = false;
	}

	/**
	 * Open the doors.
	 */
	public void openDoors() {
		System.out.println("Opening doors");
		this.doorState = true;
	}

	/**
	 * Close the doors.
	 */
	public void closeDoors() {
		System.out.println("Closing doors");
		this.doorState = false;
	}

	/**
	 * Get the doors' current open/closed status.
	 * @return True if the doors are open, false if they are closed.
	 */
	public boolean isOpen() {
		return this.doorState;
	}

}
