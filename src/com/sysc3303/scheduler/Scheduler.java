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
import com.sysc3303.commons.Message;
import com.sysc3303.commons.SerializationUtil;
import com.sysc3303.commons.SocketHandler;
import com.sysc3303.constants.Constants;


/**
 * @author
 *
 */
public class Scheduler {
	private CommunicationHandler    communicationHandler;
	private SchedulerMessageHandler messageHandler;
	//This list stores the raw data from floor, need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList<FloorButtonMessage>    floorRequestList;
	//This list stores the raw data from elevator,need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList<ElevatorButtonMessage> elevatorRequestList;
	//The elevator's position, which floor the elevator is currently at.
	private int              elevatorPosition;
	//The elevator status:going up, going down or stationary
	//constants to descripe the elevator's status
	private int              target     = -1;
	private FloorMessageHandler    floorMessageHandler;
	private ElevatorMessageHandler elevatorMessageHandler;
	private Request                request;
	private ScheudulerElevatorStatus elevatorStatus;  


	public Scheduler(int port) {
		messageHandler         = new SchedulerMessageHandler();
		communicationHandler   = new CommunicationHandler(port, messageHandler);
		//can we declare something like the struct in c here ?????????????????
		//or what is the object name in other class
		floorRequestList       = new ArrayList<FloorButtonMessage>(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorRequestList    = new ArrayList<ElevatorButtonMessage>(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorPosition       = -1;
		elevatorStatus         = SchedulerElevatorStatus.Idle;
		request                = new Request();
		elevatorMessageHandler = new ElevatorMessageHandler(messageHandler, request);
		floorMessageHandler    = new FloorMessageHandler(messageHandler, request);

		elevatorMessageHandler.start();
		floorMessageHandler.start();
			/**Handle this in multi thread...**/
			// if recieve message from floor
			  // add into floorRequestList   (synchronized)
			  // decideTargetFloor           (synchronized)
			  // create GoToFloorMessage
			  // wait for message from elevator
			/****/
			// if recieve message from elevator
			  // if elevatorStateMessage
			    // if elevator in target floor
					// create FloorArrivalMessage and send it to floor
			        // update target floor que (synchronized)
			  // if elevatorRequest message
			    // queue it!                   (synchronized)


	}

	public boolean recievedFloorButtonMessage() {
		return messageHandler.hasFloorButtonMessage();
	}

	public FloorButtonMessage getFloorButtonMessage() {
		return messageHandler.getFloorButtonMessage();
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
	
/**
  * This function set the elevatorStatus which will be used internally
	* based on the message sent from elevator.
	* @para
	*/
	public void setElevatorStatus(ElevatorStatus elevatorStatus) {
		if (elevatorStatus == ElevatorStatus.GoingUp) {
			elevatorStatus = ElevatorStatus.GoingUp;
		}
		if (elevatorStatus == ElevatorStatus.GoingDown){
			elevatorStatus = ElevatorStatus.GoingDown;
		}

		if(elevatorRequestList.size() = 0 && floorRequestList.size() = 0){
			elevatorStatus = ElevatorStatus.Idle;
		}
	}


	public Command createCommandForElevator(Message message) {
		Object[] params = {message.destinationFloor};
		return new Command("goToFloor", params);
	}
	
	public ElevatorMessageHander getElevatorVector() {
		return elevatorMessageHandler.getElevatorVector();
	}
	
	//This function update the target floor based on the floorRequestList, elevatorRequestList
	//and elevator's position and status
			
	

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
			ElevatorVector elevatorVector  = scheduler.getElevatorVector();
			int            currentPosition = elevatorVector.currentFloor;
			ElevatorStatus currentStatus   = elevatorVector.status;
			
			scheduler.setElevatorStatus(currentStatus);
			
			if(scheduler.decide_target_floor()) {
				
			}
			
			
			
		}
	}
}
