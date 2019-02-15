package com.sysc3303.scheduler;

import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.commons.Direction;
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
		int floor = ((FloorButtonMessage) message).getFloor();
		Direction direction = ((FloorButtonMessage) message).getDirection();
		new Thread(floorMessageHandler, "Floor Message Handler: floor-" + floor + " direction-" + direction).start();
	}
	
	/**
	 * Starts new thread for handling elevator message
	 * @param message
	 */
	public void startElevatorMessageHandler(Message message) {
		elevatorMessageHandler = new ElevatorRequestHandler(request, message, schedulerMessageHandler);
		int floor = ((ElevatorButtonMessage) message).getDestinationFloor();
		int elevatorId = ((ElevatorButtonMessage) message).getElevatorId();
		new Thread(elevatorMessageHandler, "Elevator Message Handler : floor-" + floor + " elevator-" + elevatorId ).start();
	}
}
