package com.sysc3303.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.sysc3303.commons.Command;
import com.sysc3303.commons.Message;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.commons.SocketHandler;
import com.sysc3303.constants.Constants;

/**
 * @author 
 *
 */
public class Scheduler {
	private SocketHandler floorSocketHandler;
	private SocketHandler elevatorSocketHandler;
	
	public Scheduler(int port) {
		floorSocketHandler    = new SocketHandler(port);
		elevatorSocketHandler = new SocketHandler();
	}
	
	public byte[] recieveMessageFromFloor(byte[] data) {
		data = floorSocketHandler.waitForPacket(data, false);
		return data;
	}

	public byte[] recieveMessageFromElevator(byte[] data) {
		data = elevatorSocketHandler.waitForPacket(data, true);
		return data;
	}
	
	public int getElevatorRecievePacketLength() {
		return elevatorSocketHandler.getRecievePacketLength();
	}

	public int getFloorRecievePacketLength() {
		return floorSocketHandler.getRecievePacketLength();
	}
	
	public void sendCommandToElevator(byte[] data, int length, InetAddress address, int port) {
		elevatorSocketHandler.sendSocket(data, address, port, length);
	}
	
	public void sendMessageToFloor(byte[] data, int length) {
		floorSocketHandler.sendSocketToRecievedHost(data, length);
	}
	
	public Command createCommandForElevator(Message message) {
		Object[] params = {message.destinationFloor};
		return new Command("goToFloor", params);
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties                 properties           = new Properties();
		InputStream                inputStream          = new FileInputStream(Constants.CONFIG_PATH);
		boolean                    running              = true;
		InetAddress                elevatorIp           = InetAddress.getLocalHost();
		SerializationUtil<Message> msgSerializationUtil = new SerializationUtil<Message>();
		SerializationUtil<Command> cmdSerializationUtil = new SerializationUtil<Command>();
		
		properties.loadFromXML(inputStream);
		
		int       port         = Integer.parseInt(properties.getProperty("schedulerPort"));
		int       elevatorPort = Integer.parseInt(properties.getProperty("elevatorPort"));
		Scheduler scheduler    = new Scheduler(port);
		
		while(running) {
			System.out.println("----------");
			System.out.println("Waiting for message from floor");
			
			byte[]  recieveData   = scheduler.recieveMessageFromFloor(new byte[300]);
			int     recieveLength = scheduler.getFloorRecievePacketLength();	
			Message message       = msgSerializationUtil.deserialize(recieveData, recieveLength);
			
			System.out.println("Recieved following message from floor: ");
			System.out.println(message.toString());
 			System.out.println("Sending command to elevator");
 			
 			
 			// let scheduler create command
 			Command command     = scheduler.createCommandForElevator(message);
 			byte[]  commandData = cmdSerializationUtil.serialize(command);
 		
 			// send created command to elevator
 			scheduler.sendCommandToElevator(commandData, commandData.length, elevatorIp, elevatorPort);
 			
 			// wait for message from elevators arrival sensor
 			// !!!need some kind of class that tells elevators state!!!
 			System.out.println("Waiting for elevator to arrive at destination");
 			
 			recieveData   = scheduler.recieveMessageFromElevator(new byte[300]);
 			recieveLength = scheduler.getFloorRecievePacketLength();
 			
 			System.out.println("Elevator arrived to destination, sending information to floor");
 			
 			// send elevator state back to floor
 			scheduler.sendMessageToFloor(recieveData, recieveLength);
 			System.out.println("----------");
		}
	}
}
