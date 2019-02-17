package com.sysc3303.elevator;




import java.util.ArrayList;
import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

/**
 * Elevator represents a physical elevator within the system. It is directed
 * by the Scheduler, and notifies the Scheduler whenever it arrives at a floor.
 * The system can have several elevators, and each will run as a separate
 * thread.
 *
 * @author Joshua Fryer, Yu Yamanaka
 *
 */
public class Elevator {
	public static final int GROUND_FLOOR = 0;
	
	public final int elevatorID;

	// Elevator components.
	private ElevatorLamp   				lamp;
	private Motor          				motor;
	private Door           				door;
	private ArrayList<ElevatorButton> 	buttons;
	private FloorSensor 				sensor;

	// Variables concerning the elevator's current status
	private int 						currentFloor;
	private int							currentHeight; // Current height in CM
	private ElevatorState 				currentState;
	private Direction					currentDirection;
	
	private Thread 						mover;

	private ElevatorMessageHandler 		messageHandler;
	private ElevatorSystem				parentSystem;
	/*
	private ElevatorState[] states = {new Idle(), new MovingUp(), 
			new MovingDown(), new OpeningDoors(), new DoorsOpen(),
			new ClosingDoors()};
			*/
	/**
	 * Class constructor.
	 * @param numFloors		The number of floors in the system.
	 * @param ID			The unique ID of this elevator.
	 * @param system		The ElevatorSystem this elevator is part of.
	 */
	public Elevator(int numFloors, int ID, ElevatorSystem system) {
		elevatorID 			= ID;
		lamp          		= new ElevatorLamp(this);
		buttons       		= generateButtons(numFloors);
		sensor 				= new FloorSensor(this);
		motor         		= new Motor(this);
		door          		= new Door(this);
		currentFloor   		= Elevator.GROUND_FLOOR;
		currentState		= new Idle();
		currentHeight 		= GROUND_FLOOR;
		currentDirection 	= Direction.IDLE;
		parentSystem 		= system;
		messageHandler		= parentSystem.getMessageHandler();
	}
	
	/**
	 * Generate a given number of buttons.
	 * @param numButtons	The number of buttons to create.
	 * @return				An ArrayList containing all of the buttons.
	 */
	private ArrayList<ElevatorButton> generateButtons(int numButtons) {
		ArrayList<ElevatorButton> buttonList = new ArrayList<ElevatorButton>();
		for(int i=0; i<numButtons; i++) {
			buttonList.add(new ElevatorButton(this, i));
		}
		return buttonList;
	}
	
	/**
	 * Get the current state. Currently unused until State pattern is
	 * implemented.
	 */
	public ElevatorState getState() {
		return this.currentState;
	}
	
	/**
	 * Change the current State.
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
		System.out.println("Received new message from scheduler: Go to floor " + targetFloor);
		// Interrupt the movement handler
		try {
			this.mover.interrupt();
		} catch (NullPointerException e) {
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
		messageHandler.sendElevatorState(v, this.elevatorID);
	}

	/**
	 * Turn off a button's light.
	 * @param floorNum	The number of the button to turn off.
	 */
	public void clearButton(int floorNum) {
		buttons.get(floorNum).turnOff();
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

	/**
	 * Update this elevator's direction.
	 * @param dir	The new Direction. IDLE if the elevator is not moving.
	 */
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
		return messageHandler;
	}

	public Thread getMovementHandler() {
		return this.mover;
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
											this.motor, this.elevatorID), "Movement Handler Elevator " + elevatorID );
		
		// Launch the mover thread. It will continue until the target floor is
		// reached, or this elevator receives a new goToFloor request. Upon
		// receiving this request, the elevator will interrupt the thread
		// and launch a new one by invoking goToFloor() again.
		mover.start();
	}

	/**
	 * Instruct the MessageHandler to build an ElevatorMoveMessage and send it
	 * to the UI to update it.
	 */
	public void updateUI() {
		messageHandler.updateUI(elevatorID, currentFloor, currentDirection,
				door.isOpen());
	}


}
