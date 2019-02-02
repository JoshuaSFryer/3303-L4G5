package com.sysc3303.elevator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Random;

import com.sysc3303.commons.Message;
//import com.sysc3303.commons.GoToFloorMessage;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.commons.SocketHandler;
import com.sysc3303.constants.Constants;

/**
 * @author Joshua Fryer, Yu Yamanaka
 *
 */


public class Elevator {
	private static final int GROUND_FLOOR = 1;
	
	public final int elevatorID;

	private SocketHandler  		socketHandler;
	private ElevatorLamp   		lamp;
	private Motor          		motor;
	private Door           		door;
	private ArrayList<ElevatorButton> 	buttons;
	private FloorSensor 		sensor;
	
	private int				targetFloor;
	private int 			currentFloor;
	
	private int			currentHeight; // Current height in CM
	private ElevatorState 	currentState;
	
	private Thread 			mover;
	/*
	private ElevatorState[] states = {new Idle(), new MovingUp(), 
			new MovingDown(), new OpeningDoors(), new DoorsOpen(),
			new ClosingDoors()};
			*/
	
	public Elevator(int port, int numFloors, int ID) {
		elevatorID 		= ID;
		socketHandler 	= new SocketHandler(port);
		lamp          	= new ElevatorLamp();
		buttons       	= generateButtons(numFloors);
		sensor 			= new FloorSensor(this);
		motor         	= new Motor(this);
		door          	= new Door();
		currentFloor   	= Elevator.GROUND_FLOOR;
		targetFloor 	= Elevator.GROUND_FLOOR;
		currentState	= new Idle();
		currentHeight 	= 0; //TODO: de-magicify this number
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
	
	/*
	public byte[] recieveMessageFromScheduler(byte data[]) {
		
		data = socketHandler.waitForPacket(data, false);
		return data;
		
		
	}
	*/
	public void receiveMessageFromScheduler() {
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

	public int getSchedulerMessageLength() {
		return socketHandler.getRecievePacketLength();
	}
	
	public void sendMessageToScheduler(byte[] data, int length) {
		socketHandler.sendSocketToRecievedHost(data, length);
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
	}
	
	public int getCurrentHeight() {
		return this.currentHeight;
	}
	
	public void setCurrentHeight(int height) {
		this.currentHeight = height;
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
		//sensor.attachThread(mover);
		
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
		SerializationUtil<Message> serializationUtil = new SerializationUtil<Message>();
		
		properties.loadFromXML(inputStream);
		
		// Create a new Elevator instance.
		int      port     = Integer.parseInt(properties.getProperty("elevatorPort"));
		Elevator elevator = new Elevator(port, 
							Integer.parseInt(properties.getProperty("numberOfElevators")),
							0); //TODO: De-magicify this number.
		
		while(running) {
			
			/*
			byte[]  recieveData = new byte[Constants.MESSSAGE_BUFFER_LENGTH];
			Message message;
			int     recieveLength;
			
			System.out.println("----------");
			System.out.println("Waiting for message from scheduler");
			
			recieveData   = elevator.recieveMessageFromScheduler(recieveData);
			recieveLength = elevator.getSchedulerMessageLength();
			message       = serializationUtil.deserialize(recieveData, recieveLength);
			
			System.out.println("Elevator recieved following message: ");
			System.out.println(message.toString());
	
			
			System.out.println("Forwarding message to scheduler");
			
			elevator.sendMessageToScheduler(recieveData, recieveLength);
	
			System.out.println("Message sent");
			System.out.println("----------");
			*/
			
			elevator.receiveMessageFromScheduler();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
