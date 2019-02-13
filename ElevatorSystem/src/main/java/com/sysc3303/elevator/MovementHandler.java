package com.sysc3303.elevator;

import com.sysc3303.commons.Direction;

public class MovementHandler implements Runnable {
	public static final int MOVEMENTDELAY = 1250;
	
	int targetFloor;
	int elevatorId;
	Motor motor;
	Elevator context;
	FloorSensor sensor;
	
	public MovementHandler(int targetFloor, Elevator context,
							FloorSensor sensor, Motor motor, int elevatorId) {
		this.targetFloor = targetFloor;
		this.motor = motor;
		this.sensor = sensor;
		this.context = context;
		this.elevatorId = elevatorId;
	}
	
	/**
	 * Move towards the target floor, not stopping until either the next floor 
	 * is reached, or the elevator is interrupted by a new request.
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
					context.setCurrentFloor(floor);
					System.out.println("Arrived at floor: " + floor);
					if(context.getCurrentFloor() == targetFloor) {
						context.setCurrentDirection(Direction.IDLE);
					}
					context.notifyArrival(targetFloor);
					context.setCurrentFloor(floor);
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
						System.out.println("Elevator Id: " + elevatorId + " arrived at destination ("+targetFloor+") !");
						context.openDoors();
						return;
					}
				}
			} catch (InterruptedException e) {
				// Thread was interrupted, so return and kill it.
				return;
			}
		}
	}
	
	public void moveUp() {
		motor.moveUp();
		System.out.println("Height: " + context.getCurrentHeight() + " cm");
	}
	
	public void moveDown() {
		motor.moveDown();
		System.out.println("Height: " + context.getCurrentHeight() + " cm");
	}

}
