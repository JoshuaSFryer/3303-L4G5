package com.sysc3303.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.sysc3303.commons.Message;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.commons.SocketHandler;

import constants.Constants;

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
	
	public void sendMessageToElevator(byte[] data, int length, InetAddress address, int port) {
		elevatorSocketHandler.sendSocket(data, address, port, length);
	}
	
	public void sendMessageToFloor(byte[] data, int length) {
		floorSocketHandler.sendSocketToRecievedHost(data, length);
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties                 properties        = new Properties();
		InputStream                inputStream       = new FileInputStream(Constants.CONFIG_PATH);
		boolean                    running           = true;
		InetAddress                elevatorIp        = InetAddress.getLocalHost();
		SerializationUtil<Message> serializationUtil = new SerializationUtil<Message>();
		
		properties.loadFromXML(inputStream);
		
		int       port         = Integer.parseInt(properties.getProperty("schedulerPort"));
		int       elevatorPort = Integer.parseInt(properties.getProperty("elevatorPort"));
		Scheduler scheduler    = new Scheduler(port);
		
		while(running) {
			byte[]  recieveData = new byte[300];
			int     recieveLength;
			Message message;
			
			System.out.println("----------");
			System.out.println("Waiting for message from floor");
			
			recieveData   = scheduler.recieveMessageFromFloor(recieveData);
			recieveLength = scheduler.getFloorRecievePacketLength();	
			message       = serializationUtil.deserialize(recieveData, recieveLength);
			
			System.out.println("Recieved following message from floor: ");
			System.out.println(message.toString());
 			System.out.println("Forwarding message to elevator");
 			
 			scheduler.sendMessageToElevator(recieveData, recieveLength, elevatorIp, elevatorPort);
 			
 			System.out.println("Wating for message from elevator");
 			
 			recieveData   = scheduler.recieveMessageFromElevator(new byte[300]);
 			recieveLength = scheduler.getElevatorRecievePacketLength();
 			message       = serializationUtil.deserialize(recieveData, recieveLength);
 			
 			System.out.println("Recieved following message from elevator: ");
 			System.out.println(message.toString());
 			System.out.println("Forwarding message to floor");
 			
 			scheduler.sendMessageToFloor(recieveData, recieveLength);
 			
 			System.out.println("Message sent");
 			System.out.println("----------");
		}
	}
}
