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
	
	public byte[] receiveMessageFromFloor(byte[] data) {
		data = floorSocketHandler.waitForPacket(data, false);
		return data;
	}

	public byte[] receiveMessageFromElevator(byte[] data) {
		data = elevatorSocketHandler.waitForPacket(data, true);
		return data;
	}
	
	public int getElevatorReceivePacketLength() {
		return elevatorSocketHandler.getReceivePacketLength();
	}

	public int getFloorReceivePacketLength() {
		return floorSocketHandler.getReceivePacketLength();
	}
	
	public void sendMessageToElevator(byte[] data, int length, InetAddress address, int port) {
		elevatorSocketHandler.sendSocket(data, address, port, length);
	}
	
	public void sendMessageToFloor(byte[] data, int length) {
		floorSocketHandler.sendSocketToReceivedHost(data, length);
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
			Message message;
			
			System.out.println("----------");
			System.out.println("Waiting for message from floor");

			receiveData   = scheduler.receiveMessageFromFloor(receiveData);
			receiveLength = scheduler.getFloorReceivePacketLength();
			message       = serializationUtil.deserialize(receiveData, receiveLength);

			
			System.out.println("Received following message from floor: ");
			System.out.println(message.toString());
 			System.out.println("Forwarding message to elevator");
 			
 			scheduler.sendMessageToElevator(receiveData, receiveLength, elevatorIp, elevatorPort);
 			
 			System.out.println("Wating for message from elevator");
 			
 			receiveData   = scheduler.receiveMessageFromElevator(new byte[400]);
 			receiveLength = scheduler.getElevatorReceivePacketLength();
 			message       = serializationUtil.deserialize(receiveData, receiveLength);
 			
 			System.out.println("Received following message from elevator: ");
 			System.out.println(message.toString());
 			System.out.println("Forwarding message to floor");
 			
 			scheduler.sendMessageToFloor(receiveData, receiveLength);
 			
 			System.out.println("Message sent");
 			System.out.println("----------");
		}
	}
}
