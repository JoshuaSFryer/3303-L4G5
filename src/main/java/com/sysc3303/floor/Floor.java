package com.sysc3303.floor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;

import com.sysc3303.communication.Message;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.communication.SocketHandler;
import com.sysc3303.constants.Constants;

/**
 * @author 
 *
 */
public class Floor {
	private final String  inputFilePath = "./src/resource/inputFile.txt";
	private final int     FLOOR_NUMBER;
	private SocketHandler socketHandler;
	private FloorButton   button;
	private FloorLamp     lamp;
	
	public Floor(int floorNumber) {
		this.FLOOR_NUMBER = floorNumber;
		socketHandler     = new SocketHandler();
		lamp              = new FloorLamp();
		button            = new FloorButton();
	}
	
	public void sendMessageToScheduler(byte[] data, InetAddress address, int port) {
		socketHandler.sendSocket(data, address, port);
	}
	
	public byte[] waitMessageFromScheduler(byte[] data) {
		byte[] receivedData = socketHandler.waitForPacket(data, true);
		return receivedData;
	}
	
	public int getSchedulerMessageLength() {
		return socketHandler.getReceivePacketLength();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Properties                 properties        = new Properties();
		InputStream                inputStream       = new FileInputStream(Constants.CONFIG_PATH);
		InetAddress                schedulerIp       = InetAddress.getLocalHost();
		int                        baseFloor         = 1;
		Floor                      floor             = new Floor(baseFloor);
		MessageUtil                messageUtil       = new MessageUtil();
		ArrayList<Message>         messages          = messageUtil.createMessageArr(Constants.INPUT_PATH);
		SerializationUtil<Message> serializationUtil = new SerializationUtil<Message>();
		
		properties.loadFromXML(inputStream);
		
		int schedulerPort = Integer.parseInt(properties.getProperty("schedulerPort"));
		
		for(int i = 0; i < messages.size(); i++) {
			Message message      = messages.get(i);
			byte[]  receivedData = new byte[400];
			byte[]  data         = serializationUtil.serialize(message);
			
			System.out.println("-------------");
			System.out.println("Sending message to Scheduler:" );
			System.out.println(message.toString());
			
			floor.sendMessageToScheduler(data, schedulerIp, schedulerPort);	
			
			System.out.println("Waiting message from scheduler");
			
			receivedData = floor.waitMessageFromScheduler(receivedData);
			
			System.out.println("Received message from scheduler");
			
			message = serializationUtil.deserialize(receivedData, floor.getSchedulerMessageLength());
			
			System.out.println(message.toString());
			System.out.println("-------------");
		}	
	}
}
