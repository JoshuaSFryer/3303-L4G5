package com.sysc3303.elevator;

/**
 * Door represents and elevator's doors. They can be open or closed.
 */
public class Door {
	
	// Open = true, closed = false.
	@SuppressWarnings("unused")
	private boolean doorState;
	private Elevator parent;
	private boolean stuckOpen;

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

	public boolean isStuckOpen() {
		return stuckOpen;
	}

	public void stick() {
		System.out.println("Elevator "+parent.elevatorID+": Doors are stuck open!");
		stuckOpen = true;
	}

	public void unstick() {
		System.out.println("Elevator "+parent.elevatorID+": Doors no longer stuck!");

		stuckOpen = false;
	}

}
