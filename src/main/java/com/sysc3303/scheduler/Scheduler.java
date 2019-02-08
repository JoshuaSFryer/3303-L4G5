package com.sysc3303.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.*;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.communication.FloorArrivalMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.constants.Constants;


/**
 * @author Yu Yamanaaka Xinrui Zhang
 * Scheduler subsystem
 */
public class Scheduler {
	private Runnable floorMessageHandler    = null;
	private Runnable elevatorMessageHandler = null;
	private Request  request; 

	public Scheduler() {		
		request  = new Request();
	}

	/**
	 * Starts new thread for handling floor message
	 * @param message
	 */
	public void startFloorMessageHandler(Message message) {
		floorMessageHandler = new FloorRequestHandler(request, (FloorButtonMessage)message);
		new Thread(floorMessageHandler).start();
	}
	
	/**
	 * Starts new thread for handling elevator message
	 * @param message
	 */
	public void startElevatorMessageHandler(Message message) {
		elevatorMessageHandler = new ElevatorRequestHandler(request, message);
		new Thread(elevatorMessageHandler).start();
	}
	
	/**
	 * 
	 * @return FloorArrivalMessage
	 */
	public FloorArrivalMessage getFloorArrivalMessage() {
		ElevatorRequestHandler elevatorMessageHandler = (ElevatorRequestHandler)this.elevatorMessageHandler;
		return elevatorMessageHandler.getFloorArrivalMessage();
	}
	
	/**
	 * @return GoToFloorMessage
	 */
	public GoToFloorMessage getGoToFloorMessage() {
		FloorRequestHandler floorMessageHandler = (FloorRequestHandler)this.floorMessageHandler;
		return floorMessageHandler.getGoToFloorMessage();
	}
		
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
		InetAddress elevatorIp  = InetAddress.getLocalHost();



		int       port         = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
		int       elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
		Scheduler scheduler    = new Scheduler();
		
		System.out.println("Starting Scheduler Message Handler...");
		SchedulerMessageHandler messageHandler = new SchedulerMessageHandler(port, scheduler);
	}
}
