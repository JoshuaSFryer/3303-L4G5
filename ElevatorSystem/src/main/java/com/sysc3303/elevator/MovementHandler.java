package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

/**
 * MovementHandler is used to control an elevator's movement. It runs as a
 * thread, continuously moving its parent elevator towards its target floor.
 */
public class MovementHandler implements Runnable {
	// How long to wait between calls of moveUp().
	// Effectively, this is how long it takes the elevator to move one unit
	// of distance up or down.

	private static int MOVEMENTDELAY;

	// How long the elevator can take between floors before declaring itself to
	// be stuck, and shutting down.
	// For now, this time is twice the time that it would ordinarily take to reach the next floor.
	private static int WATCHDOGTIME;
	//public static final int WATCHDOGTIME = MOVEMENTDELAY * 20;


	int targetFloor;
	int elevatorId;
	Motor motor;
	Elevator context;
	FloorSensor sensor;
	private boolean atFloor;
	int lastHeight;
	
	public MovementHandler(int targetFloor, Elevator context,
							FloorSensor sensor, Motor motor, int elevatorId) {
		this.targetFloor = targetFloor;
		this.motor = motor;
		this.sensor = sensor;
		this.context = context;
		this.elevatorId = elevatorId;
		this.atFloor = false;
		int timeBetweenFloors = Integer.parseInt(ConfigProperties.getInstance().getProperty("timeBetweenFloors"));
		MOVEMENTDELAY = timeBetweenFloors/FloorSensor.FLOORHEIGHT;
		WATCHDOGTIME = timeBetweenFloors * 2;
	}
	
	/**
	 * Move towards the target floor, not stopping until either the next floor 
	 * is reached, or the elevator is interrupted by a new request.
	 */
	public void run() {
		long startTime = currentTimeMillis();
		lastHeight = context.getCurrentHeight();
		// Run continuously, until this elevator's floor sensor has let us know
		// (via this.arrived) that is has reached a floor.
		while(!this.atFloor) {
			try {
				// Wait a short time between movement calls.
				Thread.sleep(MOVEMENTDELAY);

				// Get this elevator's current floor.
				int floor = context.getCurrentFloor();
				
				// Check whether the elevator has arrived at a floor.
				if(sensor.isAtFloor() && lastHeight != context.getCurrentHeight()) { //TODO: Have floor sensor interrupt this in some way instead of polling the sensor
					context.startTelemetryTimer();
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
					lastHeight = context.getCurrentHeight();
					// Reset the watchdog timer.
					System.out.println("Elevator "+ elevatorId +": Refreshing watchdog");
					startTime = currentTimeMillis();
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
						context.notifyArrival(floor);
						// Open the elevator doors.
						context.openDoors();
						// Turn off the light of the button corresponding to this floor.
						context.clearButton(targetFloor);
						// Kill.
						return;
					}
				}
				if (( currentTimeMillis() - startTime) >= WATCHDOGTIME) {
					this.targetFloor = 0;
					System.out.println("I'VE FALLEN AND I CAN'T GET UP!\n "
					+ "Killing Elevator " +elevatorId);
					context.terminateStuckElevator();
					return;
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
     * Return the current target floor.
     * @return  The target floor's number.
     */
	public int getTargetFloor() {
		return targetFloor;
	}

	/**
	 * Let this handler know that the elevator has reached a floor and should
	 */
	public void setAtFloor() {
		atFloor = true;
	}

}