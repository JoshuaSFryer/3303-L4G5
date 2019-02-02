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
	private SocketHandler    floorSocketHandler;
	private SocketHandler    elevatorSocketHandler;
	//This list stores the raw data from floor, need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList        floorRequestList[];
	//This list stores the raw data from elevator,need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList        elevatorRequestList[];
	//The elevator's position, which floor the elevator is currently at.
	private int              elevatorPosition;
	//The elevator status:going up, going down or stationary
	private int              elevatorStatus;
	//constants to descripe the elevator's status
	private static final int goingUp    = 1;
	private static final int goingDown  = 2;
	private static final int stationary = 0;
	private int              target     = -1;

	public Scheduler(int port) {
		floorSocketHandler    = new SocketHandler(port);
		elevatorSocketHandler = new SocketHandler();
		//can we declare something like the struct in c here ?????????????????
		//or what is the object name in other class
		floorRequestList[]    = new ArrayList(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorRequestList[] = new ArrayList(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorPosition      = -1;
		elevatorStatus        = stationary;
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
	
	private FloorRequest getEariestFloorRequest() {
		FloorRequest floorRequest;
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			Date         curRequestTime  = curFloorRequest.time;
			
			if(floorRequest == null || floorRequest.time.compareTo(curRequestTime) > 0){
				floorRequest = curFloorRequest;
			}
		}
		
		return floorRequest;
	}
	
	private ElevatorRequest getEariestElevatorRequest() {
		ElevatorRequest elevatorRequest;
		//go through the elevatorRequestList to find the earliest request
		//not sure what time is being named in elevator????????????????????????
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			Date            curRequestTime     = curElevatorRequest.time;
			
			if(elevatorRequest == null || elevatorRequest.time.compareTo(curRequestTime) > 0) {
				elevatorRequest = curElevatorRequest;
			}
		}
		return elevatorRequest;
	}
	
	private ArrayList<FloorRequest> getFloorUpRequestArray() {
		ArrayList<FloorRequest> selectFloorListup = new ArrayList<FloorRequest>();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.direction == requestUp && 
			   curFloorRequest.requestFloor > elevatorPosition) {//???
				selectFloorListup.add(curFloorRequest);
			}
		}
		
		return selectFloorListup;
	}
	
	private ArrayList<ElevatorRequest> getElevatorUpRequestArray() {
		ArrayList<ElevatorRequest> selectElevatorListup = new ArrayList<ElevatorRequest>();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor > elevatorPosition) {//???
				selectElevatorListup.add(curFloorRequest);
			}
		}
		
		return selectElevatorListup;
	}
	
	private ArrayList<ElevatorRequest> getEariestElevatorRequest() {
		ArrayList<ElevatorRequest> selectElevatorListup = new ArrayList<ElevatorRequest>();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor > elevatorPosition){//???
					selectElevatorListup.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListup;
	}
	
	private int getNearestUpRequest(ArrayList<FloorRequest> seletFloorListup, ArrayList<ElevatorRequest> selectElevatorListup) {
		int targetFloor = 0;
		
		for(int i = 0 ; i < selectFloorListup.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int curRequestFloor = selectFloorListup.get(i).requestFloor;
			
			if(targetFloor == 0 || targetFloor > curRequestFloor){
				targetFloor = curRequestFloor;//???
			}
		}

		for(int i = 0; i < selectElevatorListup.size(); i++) {
			int curRequestFloor = selectElevatorListup.get(i).requestFloor;
			if(targetFloor == 0 || targetFloor > curRequestFloor) {
				targetFloor = curRequestFloor;//???
			}
		}
		return targetFloor;
	}
	
	private int getNearestDownRequest(ArrayList<FloorRequest> seletFloorListDown, ArrayList<ElevatorRequest> selectElevatorListDown) {
		int targetFloor = 0;
		for(int i = 0 ;i < selectFloorListDown.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int requestFloor = selectFloorListDown.get(i).requestFloor;
			
			if(targetFloor == 0 || targetFloor < requestFloor){
				target = requestFloor;//???
			}
		}

		for(int i = 0 ; i < selectElevatorListDown.size(); i++) {
			int requestFloor = selectElevatorListDown.get(i).requestFloor;
			
			if(targetFloor == 0 || target < requestFloor){
				target = requestFloor;//???
			}
		}
		
		return targetFloor;
	}
	
	private ArrayList<FloorRequest> getFloorDownRequestArray() {
		ArrayList<FloorRequest> selectFloorListDown = new ArrayList<FloorRequest>();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			if(curFloorRequest.direction == requestDown && 
			   curFloorRequest.requestFloor < elevatorPosition){//???
				selectFloorListDown.add(curFloorRequest);
			}
		}
		
		return selectFloorListDown;
	}
	
	private ArrayList<ElevatorRequest> getElevatorDownRequestArray() {
		ArrayList<ElevatorRequest> selectElevatorListDown = new ArrayList<ElevatorRequest>();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor < elevatorPosition){//???
				selectElevatorListDown.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListDown;
	}
	
    //This function update the target floor based on the floorRequestList, elevatorRequestList
    //and elevator's position and status
	private void decideTargetFloor() {
		if(elevatorStatus == stationary) {
			// want to set to null, this may need to be changed
			FloorRequest    floorRequest    = getEariestFloorRequest();
			ElevatorRequest elevatorRequest = getEariestElevatorRequest();
			
			//NOT SURE what the objects are called!!!!!!!!!!!!!!!!!!!!
			if(floorRequest.time.compareTo(elevatorRequest.time) > 0) {
				target = elevatorRequest.numberFromElevator;
			}
			else {
				target = floorRequest.numberFromFloor;
			}
		}
		else if(elevatorStatus == goingUp) {
			ArrayList selectFloorListup    = getFloorUpRequestArray();
			ArrayList selectElevatorListup = getElevatorUpRequestArray();

			//inefficient sorting algothrim
			//go through the selectFloorList to find the nearest request
			target = getNearestUpRequest(selectFloorListup, selectElevatorListup);
		}
		else {
			ArrayList selectFloorListDown    = getFloorDownRequestArray();
			ArrayList selectElevatorListDown = getElevatorDownRequestArray();

			//inefficient sorting algothrim
			//go through the selectFloorList to find the nearest request
			target = getNearestDownRequest(selectFloorListDown, selectElevatorListDown);
		}
	}

	/**
    * need another function which is to update the floorRequestList and elevatorRequestList
	* once the elevator arrived a target floor, the target floor needs to be removed potentially
	* both from floorRequestList and elevatorRequestList.
	* for elevatorRequestList: just scan and removed
	* for floorRequestList: need to scan the target floor with the direction
	*/
	private updateRequestList(){

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
