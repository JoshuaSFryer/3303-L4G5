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
	private SocketHandler floorSocketHandler;
	private SocketHandler elevatorSocketHandler;
	//This list stores the raw data from floor, need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList floorRequestList[];
	//This list stores the raw data from elevator,need to add <type>!!!!!!!!!!!!!!!!!!!!
	private ArrayList elevatorRequestList[];
	//The elevator's position, which floor the elevator is currently at.
	private int elevatorPosition;
	//The elevator status:going up, going down or stationary
	private int elevatorStatus;
	//constants to descripe the elevator's status
	private static final int goingUp = 1;
	private static final int goingDown = 2;
	private static final int stationary = 0;
	private int target = -1;

	public Scheduler(int port) {
		floorSocketHandler    = new SocketHandler(port);
		elevatorSocketHandler = new SocketHandler();
		//can we declare something like the struct in c here ?????????????????
		//or what is the object name in other class
		floorRequestList[] = new ArrayList(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorRequestList[] = new ArrayList(); //need to add <type>!!!!!!!!!!!!!!!!!!!!
		elevatorPosition = -1;
		elevatorStatus = stationary;

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

//This function update the target floor based on the floorRequestList, elevatorRequestList
//and elevator's position and status
	private void decide_target_floor(){
		switch (elevatorStatus){
			case stationary:
				// want to set to null, this may need to be changed
				Date firstRequest = null;
				OBject rightRequest = null;


				//go through the floorRequestList to find the earliest request
				for(int i = 0 ;i < floorRequestList.size();i++){
					//not sure what time is being named in floor????????????????????????
					if(i == 0 || firstRequest.compareTo(floorRequestList.get(i).time) > 0){
						firstRequest = floorRequestList.get(i).time;
						rightRequest = floorRequestList.get(i);
					}
				}

				//go through the elevatorRequestList to find the earliest request
				//not sure what time is being named in elevator????????????????????????
				for(int j = 0 ;j < elevatorRequestList.size();j++){
					if((firstRequest = null && j = 0)||
								firstRequest.compareTo(elevatorRequestList.get(j).time) > 0){
						firstRequest = elevatorRequestList.get(j).time;
						rightRequest = elevatorRequestList.get(j);
					}
				}
				//NOT SURE what the objects are called!!!!!!!!!!!!!!!!!!!!
				if(rightRequest == floorRequestListObject)
					target =  rightRequest.numberFromFloor;//???
				target = rightRequest.numberFromElevator;//???
				break;

			case goingUp:

				ArrayList selectFloorListup = new ArrayList();
				ArrayList selectElevatorListup = new ArrayList();

				for(int i = 0 ;i < floorRequestList.size();i++){
					//not sure what time is being named in floor????????????????????????
					if(floorRequestList.get(i).direction == requestUp
						&& floorRequestList.get(i).requestFloor > floorThatElevatorIsAt){//???
						selectFloorListup.add(floorRequestList.get(i));
					}
				}

				for(int j = 0 ;j < elevatorRequestList.size();j++){
					if(elevatorRequestList.get(j).requestFloor > floorThatElevatorIsAt){//???
							selectElevatorListup.add(elevatorRequestList.get(j));
					}
				}

				//inefficient sorting algothrim
				//go through the selectFloorList to find the nearest request
				for(int i = 0 ;i < selectFloorListup.size();i++){
					//not sure what time is being named in floor????????????????????????
					if(i == 0 || target > selectFloorListup.get(i)){
						target = selectFloorListup.get(i).requestFloor;//???
					}
				}

				for(int j = 0 ;j < selectElevatorListup.size();j++){
					if((target == -1 && j = 0)||
								target > selectElevatorListup.get(j)){
						target = selectElevatorListup.get(j).requestFloor;//???
					}
				}

				break;
			case goingDown:

				ArrayList selectFloorListDown = new ArrayList();
				ArrayList selectElevatorListDown = new ArrayList();

				for(int i = 0 ;i < floorRequestList.size();i++){
					//not sure what time is being named in floor????????????????????????
					if(floorRequestList.get(i).direction == requestDown
						&& floorRequestList.get(i).requestFloor < floorThatElevatorIsAt){//???
						selectFloorListDown.add(floorRequestList.get(i));
					}
				}

				for(int j = 0 ;j < elevatorRequestList.size();j++){
					if(elevatorRequestList.get(j).requestFloor < floorThatElevatorIsAt){//???
							selectElevatorListDown.add(elevatorRequestList.get(j));
					}
				}

				//inefficient sorting algothrim
				//go through the selectFloorList to find the nearest request
				for(int i = 0 ;i < selectFloorListDown.size();i++){
					//not sure what time is being named in floor????????????????????????
					if(i == 0 || target < selectFloorListDown.get(i)){
						target = selectFloorListDown.get(i).requestFloor;//???
					}
				}

				for(int j = 0 ;j < selectElevatorListDown.size();j++){
					if((target == -1 && j = 0)||
								target < selectElevatorListDown.get(j)){
						target = selectElevatorListDown.get(j).requestFloor;//???
					}
				}
				break;
		}

	}

	/**
  * need another function which is to update the floorRequestList and elevatorRequestList
	* once the elevator arrived a target floor, the target floor needs to be removed potentially
	* both from floorRequestList and elevatorRequestList.
	* for elevatorRequestList: just scan and removed
	* for floorRequestList: need to scan the target floor with the direction
	*/

	private update_requestList(){

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
			System.out.print ln("Waiting for message from floor");

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
