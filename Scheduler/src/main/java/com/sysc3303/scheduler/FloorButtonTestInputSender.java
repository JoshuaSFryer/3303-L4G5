package com.sysc3303.scheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.communication.SocketHandler;

/**
 * Used for testing purpose.
 * Sends FloorButtonMessage from inputFile
 * @author Yu Yamanaka
 *
 */
public class FloorButtonTestInputSender {
	private SocketHandler socketHandler;

	public FloorButtonTestInputSender() {
		socketHandler = new SocketHandler();
	}
	
	/**
	 * sends message to scheduler
	 * @param data
	 * @param address
	 * @param port
	 */
	public void sendMessageToScheduler(byte[] data, InetAddress address, int port) {
		socketHandler.sendSocket(data, address, port);
	}
	
	/**
	 * waits message from scheduler
	 * @param data
	 * @return
	 */
	public byte[] waitMessageFromScheduler(byte[] data) {
		byte[] recievedData = socketHandler.waitForPacket(data, true);
		return recievedData;
	}
	
	/**
	 * creates floorbuttonmessage from input
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<FloorButtonMessage> createMessageArr(String filePath) throws FileNotFoundException, IOException, ParseException {
		ArrayList<FloorButtonMessage> messageArr = new ArrayList<FloorButtonMessage>();
		SimpleDateFormat   formatter  = new SimpleDateFormat("hh:mm:ss.S");
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] splittedStr      = line.split("\\s+");
		       Date     time             = new Date(formatter.parse(splittedStr[0]).getTime());
		       int      floor            = Integer.parseInt(splittedStr[1]);
		       String   directionStr     = splittedStr[2];
		       Direction direction;
		       
		       if(directionStr.equals("Up")) {
		    	   direction = Direction.UP;
		       }
		       else {
		    	   direction = Direction.DOWN;
		       }
		       
		       FloorButtonMessage  message = new FloorButtonMessage(floor, direction, time, 1000);
		       messageArr.add(message);
		    }
		}
		
		return messageArr;
}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, InterruptedException {
		InetAddress                schedulerIp       = InetAddress.getLocalHost();
		FloorButtonTestInputSender   testInputSender   = new FloorButtonTestInputSender();
		ArrayList<FloorButtonMessage> messages       = testInputSender.createMessageArr("./Simulator/src/main/resources/testEvents.txt");
		SerializationUtil<FloorButtonMessage> serializationUtil = new SerializationUtil<FloorButtonMessage>();
		
		int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
		
		for(int i = 0; i < messages.size(); i++) {
			FloorButtonMessage message  = messages.get(i);
			byte[]  data                = serializationUtil.serialize(message);
			
			System.out.println("-------------");
			System.out.println("Sending message to Scheduler:" );
			System.out.println(message.toString());
			
			testInputSender.sendMessageToScheduler(data, schedulerIp, schedulerPort);	
			
			Thread.sleep(8000);
			System.out.println("-------------");
		}	
	}
}
