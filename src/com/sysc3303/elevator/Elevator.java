package com.sysc3303.elevator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class Elevator {

	private SocketHandler  socketHandler;
	private ElevatorLamp   lamp;
	private ElevatorButton button;
	private Motor          motor;
	private Door           door;

	public Elevator(int port) {
		socketHandler = new SocketHandler(port);
		lamp          = new ElevatorLamp();
		button        = new ElevatorButton();
		motor         = new Motor();
		door          = new Door();
	}
	
	public byte[] recieveMessageFromScheduler(byte data[]) {
		data = socketHandler.waitForPacket(data, false);
		return data;		
	}

	public int getSchedulerMessageLength() {
		return socketHandler.getRecievePacketLength();
	}
	
	public void sendMessageToScheduler(byte[] data, int length) {
		socketHandler.sendSocketToRecievedHost(data, length);
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties                 properties        = new Properties();
		InputStream                inputStream       = new FileInputStream(Constants.CONFIG_PATH);
		boolean                    running           = true;
		SerializationUtil<Message> serializationUtil = new SerializationUtil<Message>();
		
		properties.loadFromXML(inputStream);
		
		int      port     = Integer.parseInt(properties.getProperty("elevatorPort"));
		Elevator elevator = new Elevator(port);
		
		while(running) {
			byte[]  recieveData = new byte[300];
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
		}
	}
}
