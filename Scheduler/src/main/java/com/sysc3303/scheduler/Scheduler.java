package com.sysc3303.scheduler;

import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.Message;

/**
 * Stores request from floor and elevators
 * and starts thread for handling them
 * @author Yu Yamanaka Xinrui Zhang
 */
public class Scheduler {
	private Runnable                floorMessageHandler;
	private Runnable                elevatorMessageHandler;
	private Request                 request; 
	private SchedulerMessageHandler schedulerMessageHandler;
	
	public Scheduler(SchedulerMessageHandler schedulerMessageHandler) {		
		this.request                 = new Request();
		this.schedulerMessageHandler = schedulerMessageHandler;
	}

	/**
	 * Starts new thread for handling floor message
	 * @param message
	 */
	public void startFloorMessageHandler(Message message) {
		floorMessageHandler = new FloorRequestHandler(request, (FloorButtonMessage)message, schedulerMessageHandler);
		new Thread(floorMessageHandler).start();
	}
	
	/**
	 * Starts new thread for handling elevator message
	 * @param message
	 */
	public void startElevatorMessageHandler(Message message) {
		elevatorMessageHandler = new ElevatorRequestHandler(request, message, schedulerMessageHandler);
		new Thread(elevatorMessageHandler).start();
	}
}
