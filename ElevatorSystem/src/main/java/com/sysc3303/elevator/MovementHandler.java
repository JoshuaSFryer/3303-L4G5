package com.sysc3303.elevator;

import com.sysc3303.commons.Direction;

public class MovementHandler implements Runnable {
	public static final int MOVEMENTDELAY = 1250;
	
	int targetFloor;
	int elevatorId;
	Motor motor;
	Elevator context;
	FloorSensor sensor;
	private boolean atFloor;
	
	public MovementHandler(int targetFloor, Elevator context,
							FloorSensor sensor, Motor motor, int elevatorId) {
		this.targetFloor = targetFloor;
		this.motor = motor;
		this.sensor = sensor;
		this.context = context;
		this.elevatorId = elevatorId;
		this.atFloor = false;
	}
	
	/**
	 * Move towards the target floor, not stopping until either the next floor 
	 * is reached, or the elevator is interrupted by a new request.
	 */
	public void run() {
		// Run continuously, until this elevator's floor sensor has let us know
		// (via this.arrived) that is has reached a floor.
		while(!this.atFloor) {
			try {
				// Wait a short time between movement calls.
				Thread.sleep(MOVEMENTDELAY);

				// Get this elevator's current floor.
				int floor = context.getCurrentFloor();
				
				// Check whether the elevator has arrived at a floor.
				if(sensor.isAtFloor()) { //TODO: Have floor sensor interrupt this in some way instead of polling the sensor
					floor = sensor.getFloor();
					System.out.println("Elevator " + context.elevatorID + ": Arrived at floor: " + floor);
					// If the current floor is the target floor, set the
					// direction to Idle, as we want to stop.
					if(context.getCurrentFloor() == targetFloor) {
						context.setCurrentDirection(Direction.IDLE);
					}
					// Have the elevator notify the scheduler that it arrived.
					context.notifyArrival(targetFloor);
					// Update the elevator's current floor.
					context.setCurrentFloor(floor);
					// Update the UI.
					context.updateUI();
				}
				
				// If not at a floor yet, move towards the target.
				if(targetFloor > floor) {
					moveUp();

				} else if(targetFloor < floor) {
					moveDown();
				} else { // already at the target floor
					
					// If the elevator is partway to the next floor, move down
					// to the base of the floor.
					if(context.getCurrentHeight() > 
						context.getCurrentFloor() * FloorSensor.FLOORHEIGHT) {
						moveDown();
					} else {
						// Don't need to move any more, so kill this thread.
						System.out.println("Elevator " + elevatorId +
								": arrived at destination ("+targetFloor+") !");
						context.openDoors();
						context.clearButton(targetFloor);
						return;
					}
				}
			} catch (InterruptedException e) {
				// Thread was interrupted, so return and kill it.
				return;
			}
		}
	}

	/**
	 * Move the elevator up by an atomic distance.
	 */
	public void moveUp() {
		motor.moveUp();
		System.out.println("Elevator " +context.elevatorID+ ": Height: " + context.getCurrentHeight() + " cm");
	}

	/**
	 * Move the elevator down by an atomic distance.
	 */
	public void moveDown() {
		motor.moveDown();
		System.out.println("Elevator " +context.elevatorID+ ": Height: " + context.getCurrentHeight() + " cm");
	}

	/**
	 * Let this handler know that the elevator has reached a floor and should
	 */
	public void setAtFloor() {
		atFloor = true;
	}

}
