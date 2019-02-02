package com.sysc3303.elevator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	public byte[] receiveMessageFromScheduler(byte data[]) {
		data = socketHandler.waitForPacket(data, false);
		return data;		
	}

	public int getSchedulerMessageLength() {
		return socketHandler.getReceivePacketLength();
	}
	
	public void sendMessageToScheduler(byte[] data, int length) {
		socketHandler.sendSocketToReceivedHost(data, length);
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
			byte[]  receiveData = new byte[300];
			Message message;
			int     receiveLength;
			
			System.out.println("----------");
			System.out.println("Waiting for message from scheduler");
			
			receiveData   = elevator.receiveMessageFromScheduler(receiveData);
			receiveLength = elevator.getSchedulerMessageLength();
			message       = serializationUtil.deserialize(receiveData, receiveLength);
			
			System.out.println("Elevator received following message: ");
			System.out.println(message.toString());
	
			
			System.out.println("Forwarding message to scheduler");
			
			elevator.sendMessageToScheduler(receiveData, receiveLength);
	
			System.out.println("Message sent");
			System.out.println("----------");
		}
	}
}
