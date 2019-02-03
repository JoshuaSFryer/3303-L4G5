package com.sysc3303.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.*;
// import java.util.InvalidPropertiesFormatException;
// import java.util.Properties;
// import java.util.ArrayList;

import com.sysc3303.commons.Command;
import com.sysc3303.commons.FloorArrivalMessage;
import com.sysc3303.commons.FloorButtonMessage;
import com.sysc3303.commons.GoToFloorMessage;
import com.sysc3303.commons.Message;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.constants.Constants;


/**
 * @author
 *
 */
public class Scheduler {
	private Runnable floorMessageHandler    = null;
	private Runnable elevatorMessageHandler = null;
	private Request  request; 


	public Scheduler() {		
		request  = new Request();
	}
	
	public void startFloorMessageHandler(Message message) {
		Runnable floorMessageHandler = new FloorMessageHandler(request, (FloorButtonMessage)message);
		new Thread(floorMessageHandler).start();
	}
	
	public void startElevatorMessageHandler(Message message) {
		Runnable elevatorMessageHandler = new ElevatorMessageHandler(request, message);
		new Thread(elevatorMessageHandler).start();
	}
	
	public FloorArrivalMessage getFloorArrivalMessage() {
		ElevatorMessageHandler elevatorMessageHandler = (ElevatorMessageHandler)this.elevatorMessageHandler;
		return elevatorMessageHandler.getFloorArrivalMessage();
	}
	
	public GoToFloorMessage getGoToFloorMessage() {
		FloorMessageHandler floorMessageHandler = (FloorMessageHandler)this.floorMessageHandler;
		return floorMessageHandler.getGoToFloorMessage();
	}
	
	//This function update the target floor based on the floorRequestList, elevatorRequestList
	//and elevator's position and status		
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties  properties  = new Properties();
		InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
		InetAddress elevatorIp  = InetAddress.getLocalHost();

		properties.loadFromXML(inputStream);
		
		int       port         = Integer.parseInt(properties.getProperty("schedulerPort"));
		int       elevatorPort = Integer.parseInt(properties.getProperty("elevatorPort"));
		Scheduler scheduler    = new Scheduler();
		
		SchedulerMessageHandler messageHandler = new SchedulerMessageHandler(port, scheduler);
	}
}
