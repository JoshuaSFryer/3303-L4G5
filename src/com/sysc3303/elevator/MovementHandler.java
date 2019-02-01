package com.sysc3303.elevator;

public class MovementHandler implements Runnable {
	public static final int MOVEMENTDELAY = 100;
	
	int targetFloor;
	Motor motor;
	Elevator context;
	FloorSensor sensor;
	
	public MovementHandler(int targetFloor, Elevator context,
							FloorSensor sensor, Motor motor) {
		this.targetFloor = targetFloor;
		this.motor = motor;
		this.sensor = sensor;
		this.context = context;
	}
	
	/**
	 * Move towards the target floor until either the next floor is reached,
	 * or the elevator is interrupted by a new request.
	 */
	public void run() {
		while(true) {
			try {
				// Wait a short time between movement calls.
				Thread.sleep(MOVEMENTDELAY);
				
				int floor = context.getCurrentFloor();
				
				// Check whether the elevator has arrived at a floor.
				if(sensor.isAtFloor()) {
					floor = sensor.getFloor();
					System.out.println("Arrived at floor: " + floor);
					context.setCurrentFloor(floor);
				}
				
				// If not at a floor yet, move towards the target.
				if(targetFloor > floor) {
					moveUp();
				} else if(targetFloor > floor) {
					moveDown();
				} else { // already at the target floor
					// Don't need to move any more, so kill this thread.
					return;
				}
			} catch (InterruptedException e) {
				// Thread was interrupted, so return and kill it.
				return;
			}
		}
	}
	
	public void moveUp() {
		motor.moveUp();
	}
	
	public void moveDown() {
		motor.moveDown();
	}

}
