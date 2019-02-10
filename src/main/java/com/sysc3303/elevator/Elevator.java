package com.sysc3303.elevator;

import java.io.IOException;

import java.util.ArrayList;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;

/**
 * @author Joshua Fryer, Yu Yamanaka
 *
 */
public class Elevator {
	private static final int GROUND_FLOOR = 1;
	
	public final int elevatorID;

	private ElevatorLamp   		lamp;
	private Motor          		motor;
	private Door           		door;
	private ArrayList<ElevatorButton> 	buttons;
	private FloorSensor 		sensor;
	
	private int 			currentFloor;
	
	private int				currentHeight; // Current height in CM
	private ElevatorState 	currentState;
	private Direction		currentDirection;
	
	private Thread 			mover;
	
	private ElevatorMessageHandler messageHandler;
	/*
	private ElevatorState[] states = {new Idle(), new MovingUp(), 
			new MovingDown(), new OpeningDoors(), new DoorsOpen(),
			new ClosingDoors()};
			*/
	/**
	 * Class constructor.
	 * @param port			The port for the elevator to bind to.
	 * @param numFloors		The number of floors in the system.
	 * @param ID			The unique ID of this elevator.
	 */
	public Elevator(int port, int numFloors, int ID) {
		elevatorID 		= ID;
		lamp          	= new ElevatorLamp();
		buttons       	= generateButtons(numFloors);
		sensor 			= new FloorSensor(this);
		motor         	= new Motor(this);
		door          	= new Door();
		currentFloor   	= Elevator.GROUND_FLOOR;
		currentState	= new Idle();
		currentHeight 	= 0; //TODO: de-magicify this number
		currentDirection = Direction.IDLE;
		
		messageHandler = ElevatorMessageHandler.getInstance(port, this);
	}
	
	/**
	 * Generate a given number of buttons.
	 * @param numButtons	The number of buttons to create.
	 * @return				An ArrayList containing all of the buttons.
	 */
	private ArrayList<ElevatorButton> generateButtons(int numButtons) {
		ArrayList<ElevatorButton> buttonList = new ArrayList<ElevatorButton>();
		for(int i=0; i<numButtons; i++) {
			buttonList.add(new ElevatorButton(this, i+1));
		}
		return buttonList;
	}
	
	/**
	 * Get the current state.
	 * @return	The current ElevatorState
	 */
	public ElevatorState getState() {
		return this.currentState;
	}
	
	/**
	 * Change the current state.
	 * Perform the exit action of the state being left, change the current
	 * state to the new one, and perform the new state's entry action.
	 * @param state	The ElevatorState to switch to.
	 */
	public void setState(ElevatorState state) {
		this.currentState.exitAction(this);
		this.currentState = state;
		this.currentState.entryAction(this);
		this.currentState.doAction(this);
	}
	
	/**
	 * Handle a new instruction received from the scheduler.
	 * Interrupt the old movement and start moving towards the new target floor.
	 * @param targetFloor	The number of the new target floor
	 */
	public void receiveMessageFromScheduler(int targetFloor) {
		System.out.println("Received new message from scheduler");
		// Interrupt the movement handler
		try {
			this.mover.interrupt();
		} catch (NullPointerException e) {
			//e.printStackTrace();
			System.out.println("No movement thread to interrupt!");
		}
		// Assign the new target floor and move towards it.
		goToFloor(targetFloor);
	}
	
	/**
	 * Instruct the elevator to go to a random floor.
	 * This method should only be used for testing purposes.
	 */
	@SuppressWarnings("unused")
	private void generateRandomRequest() {
		// Interrupt the movement handler
		try {
			this.mover.interrupt();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		// Assign the new target floor
		int nextFloor = generateRandomInt(1, 8);
		System.out.println("Requested floor: " + nextFloor);
		goToFloor(nextFloor);
	}
	
	/**
	 * Generate a random integer between two values.
	 * @param min	The lower bound.
	 * @param max	The upper bound.
	 * @return	The random result.
	 */
	private int generateRandomInt(int min, int max) {
		return (int)(Math.random() * ((max-min) + 1) + min);
	}
	
	/**
	 * Notify the scheduler that the elevator has arrived at a floor.
	 * @param targetFloor	The floor the elevator has arrived at.
	 */
	public void notifyArrival(int targetFloor) {
		ElevatorVector v = new ElevatorVector(this.currentFloor, this.currentDirection, targetFloor);
		this.messageHandler.sendElevatorState(v, this.elevatorID);
	}
	
	/**
	 * Open this elevator's doors.
	 */
	public void openDoors() {
		door.openDoors();
	}
	
	/**
	 * Close this elevator's doors.
	 */
	public void closeDoors() {
		door.closeDoors();
	}

	public void setCurrentDirection(Direction dir) { this.currentDirection = dir;}
	
	/**
	 * Get this elevator's current floor.
	 * Note that if the elevator is partway between floors, this will return
	 * the last floor it visited.
	 * @return	The current floor number.
	 */
	public int getCurrentFloor() {
		return this.currentFloor;
	}
	
	/**
	 * Set the current floor of the elevator, and update its lamp.
	 * @param floor	The new floor number.
	 */
	public void setCurrentFloor(int floor) {
		this.currentFloor = floor;
		this.lamp.setCurrentFloor(floor, currentDirection);
	}
	
	/**
	 * Get this elevator's current height above ground level.
	 * @return	The current height, in centimeters.
	 */
	public int getCurrentHeight() {
		return this.currentHeight;
	}
	
	/**
	 * Set this elevator's current height above ground level.
	 * The elevator should only ever be raised or lowered by its motor, so this
	 * method should not be called by any other class.
	 * @param height	The elevator's new height, in centimeters.
	 */
	protected void setCurrentHeight(int height) {
		this.currentHeight = height;
	}
	
	/**
	 * Access this elevator's MessageHandler, to make calls to it.
	 * @return	The MessageHandler instance used by this elevator.
	 */
	public ElevatorMessageHandler getMessageHandler() {
		return this.messageHandler;
	}
	
	/**
	 * Simulate a press of one of this elevator's buttons and notify the 
	 * scheduler of the new corresponding floor request.
	 * @param num	The floor number of the button to press.
	 */
	public void pressButton(int num) {
		if(num > this.buttons.size() || num < 0) {
			//TODO: Throw an exception here?
			System.out.println("Invalid button press, out of bounds!");
			return;
		}
		this.buttons.get(num).press();
		// Notify the scheduler that a 
		messageHandler.sendElevatorButton(num, this.elevatorID);
	}
	
	
	/**
	 * Travel to the target floor. If interrupted by a message from the
	 * scheduler, stop moving and execute the new command.
	 * @param targetFloor	The number of the floor to travel to.
	 */
	public void goToFloor(int targetFloor) {
		// Close the doors before proceeding. Safety first!
		closeDoors();
		// Set the current direction and update the lamp.
		if(targetFloor > currentFloor) {
			this.currentDirection = Direction.UP;
		} else if (targetFloor < currentFloor) {
			this.currentDirection = Direction.DOWN;
		} else {
			this.currentDirection = Direction.IDLE;
		}
		this.lamp.setCurrentFloor(currentFloor, currentDirection);
		
		this.mover = new Thread(
						new MovementHandler(targetFloor, this, this.sensor, 
											this.motor));
		 
		// Launch the mover thread. It will continue until the target floor is
		// reached, or this elevator receives a new goToFloor request. Upon
		// receiving this request, the elevator will interrupt the thread
		// and launch a new one by invoking goToFloor() again.
		
		mover.start();
	}
	
	public static void main(String[] args) throws IOException {
		boolean                    running           = true;
		
		// Create a new Elevator instance.
		int      port     = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
		Elevator elevator = new Elevator(port, 
							Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfFloors")),
							0); //TODO: De-magicify this number.
		
		while(running) {

		}
	}
}
