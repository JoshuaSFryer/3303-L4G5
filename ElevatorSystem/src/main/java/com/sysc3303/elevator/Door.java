package com.sysc3303.elevator;

/**
 * Door represents and elevator's doors. They can be open or closed.
 */
public class Door {
	
	// Open = true, closed = false.
	@SuppressWarnings("unused")
	private boolean doorState;
	private Elevator parent;

	/**
	 * Class constructor.
	 */
	public Door(Elevator parent) {
		// Doors default to closed.
		this.doorState = false;
		this.parent = parent;
	}

	/**
	 * Open the doors.
	 */
	public void openDoors() {
		System.out.println("Elevator "+parent.elevatorID+": Opening doors");
		this.doorState = true;
	}

	/**
	 * Close the doors.
	 */
	public void closeDoors() {
		System.out.println("Elevator "+parent.elevatorID+": Closing doors");
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
