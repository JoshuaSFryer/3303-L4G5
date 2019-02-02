package com.sysc3303.elevator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.ArrayList;

import com.sysc3303.commons.Direction;
//import com.sysc3303.commons.Message;
//import com.sysc3303.commons.SerializationUtil;
//import com.sysc3303.commons.SocketHandler;
import com.sysc3303.constants.Constants;

/**
 * @author Joshua Fryer, Yu Yamanaka
 *
 */


public class Elevator {
	private static final int GROUND_FLOOR = 1;
	
	public final int elevatorID;

	//private SocketHandler  		socketHandler;
	private ElevatorLamp   		lamp;
	private Motor          		motor;
	private Door           		door;
	private ArrayList<ElevatorButton> 	buttons;
	private FloorSensor 		sensor;
	
	//private int				targetFloor;
	private int 			currentFloor;
	
	private int			currentHeight; // Current height in CM
	private ElevatorState 	currentState;
	private Direction		currentDirection;
	
	private Thread 			mover;
	
	private ElevatorMessageHandler messageHandler;
	/*
	private ElevatorState[] states = {new Idle(), new MovingUp(), 
			new MovingDown(), new OpeningDoors(), new DoorsOpen(),
			new ClosingDoors()};
			*/
	
	public Elevator(int port, int numFloors, int ID) {
		elevatorID 		= ID;
		//socketHandler 	= new SocketHandler(port);
		lamp          	= new ElevatorLamp();
		buttons       	= generateButtons(numFloors);
		sensor 			= new FloorSensor(this);
		motor         	= new Motor(this);
		door          	= new Door();
		currentFloor   	= Elevator.GROUND_FLOOR;
		//targetFloor 	= Elevator.GROUND_FLOOR;
		currentState	= new Idle();
		currentHeight 	= 0; //TODO: de-magicify this number
		currentDirection = Direction.IDLE;
		
		messageHandler = new ElevatorMessageHandler(port, this);
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

	public void receiveMessageFromScheduler(int targetFloor) {
		// Wait for message
		// Interrupt the movement handler
		try {
			this.mover.interrupt();
		} catch (NullPointerException e) {
			//e.printStackTrace();
			System.out.println("No movement thread to interrupt!");
		}
		// Assign the new target floor
		goToFloor(targetFloor);
	}
	
	public void generateRandomRequest() {
		// Wait for message
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
	
	private int generateRandomInt(int min, int max) {
		//Random r = new Random();
		return (int)(Math.random() * ((max-min) + 1) + min);
		
	}

	public void notifyArrival() {
		ElevatorVector v = new ElevatorVector(this.currentFloor, this.currentDirection);
		this.messageHandler.sendElevatorState(v, this.elevatorID);
	}
	
	public void openDoors() {
		door.openDoors();
	}
	
	public void closeDoors() {
		door.closeDoors();
	}
	
	public int getCurrentFloor() {
		return this.currentFloor;
	}
	
	public void setCurrentFloor(int floor) {
		this.currentFloor = floor;
		this.lamp.setCurrentFloor(floor);
	}
	
	public int getCurrentHeight() {
		return this.currentHeight;
	}
	
	public void setCurrentHeight(int height) {
		this.currentHeight = height;
	}
	
	public ElevatorMessageHandler getMessageHandler() {
		return this.messageHandler;
	}
	
	public void pressButton(int num) {
		if(num > this.buttons.size() || num < 1) {
			//TODO: Throw an exception here?
			System.out.println("Invalid button press, out of bounds!");
			return;
		}
		this.buttons.get(num+1).press();
		messageHandler.sendElevatorButton(num, this.elevatorID);
	}
	
	
	/**
	 * Travel to the target floor. If interrupted by a message from the
	 * scheduler, stop moving and execute the new command.
	 * @param targetFloor	The floor to travel to.
	 */
	public void goToFloor(int targetFloor) {
		
		this.mover = new Thread(
						new MovementHandler(targetFloor, this, this.sensor, 
											this.motor));
		
		// Launch the mover thread. It will continue until the target floor is
		// reached, or this elevator receives a new goToFloor request. Upon
		// receiving this request, the elevator will interrupt the thread
		// and launch a new one by invoking goToFloor() again.
		mover.start();
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties                 properties        = new Properties();
		InputStream                inputStream       = new FileInputStream(Constants.CONFIG_PATH);
		//InputStream                inputStream       = new FileInputStream("config/config.xml");
		boolean                    running           = true;
		//SerializationUtil<Message> serializationUtil = new SerializationUtil<Message>();
		
		properties.loadFromXML(inputStream);
		
		// Create a new Elevator instance.
		int      port     = Integer.parseInt(properties.getProperty("elevatorPort"));
		Elevator elevator = new Elevator(port, 
							Integer.parseInt(properties.getProperty("numberOfElevators")),
							0); //TODO: De-magicify this number.
		
		while(running) {
			elevator.generateRandomRequest();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
