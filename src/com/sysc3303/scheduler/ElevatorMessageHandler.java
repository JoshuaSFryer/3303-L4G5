package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorButtonMessage;
import com.sysc3303.commons.ElevatorStateMessage;
import com.sysc3303.commons.FloorArrivalMessage;
import com.sysc3303.commons.FloorButtonMessage;
import com.sysc3303.commons.Message;
import com.sysc3303.elevator.ElevatorVector;

/**
 * Handle the message from elevator
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class ElevatorMessageHandler implements Runnable {
	private Request  request;
	private Message  message;
	private FloorArrivalMessage floorArrivalMessage;
	
	/**
	 * elevator message handler constructor
	 * @para request
	 * @para message
	 */
	public ElevatorMessageHandler(Request request, Message message) {
		this.request = request;
		this.message = message;         
	}
	

	public void run() {
		if(message instanceof ElevatorStateMessage) {
			ElevatorStateMessage message          = (ElevatorStateMessage)this.message;
			ElevatorVector       elevatorVector   = message.getElevatorVector(); int                  destinationFloor = elevatorVector.targetFloor;
		   
			request.setElevatorVector(elevatorVector);
			
			if(elevatorVector.currentFloor != destinationFloor) {
				return;
			}	
	
			removeTargetFloor(destinationFloor, elevatorVector.currentDirection);
			floorArrivalMessage = new FloorArrivalMessage(destinationFloor, elevatorVector.currentDirection);
		
			// dont forget to create message and send it to floor
		}
		else if(message instanceof ElevatorButtonMessage) {
			ElevatorButtonMessage message = (ElevatorButtonMessage)this.message;
			request.addElevatorButtonMessage(message);
		}

	}
	/**
	 * @return floorArrialMessage
	 */
	public FloorArrivalMessage getFloorArrivalMessage() {
		return floorArrivalMessage;
	}
	
	/**
	 * This function is to update the floorRequestList and elevatorRequestList
	 * once the elevator arrived a target floor, the target floor needs to be removed potentially
	 * both from floorRequestList and elevatorRequestList.
	 * 
	 * @para targetFloor
	 * @para targetDirection
	 */
	private void removeTargetFloor(int targetFloor, Direction targetDirection) {
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray();
		ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				elevatorRequestList.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.getFloor()     == targetFloor && 
			   curFloorRequest.getDirection() == targetDirection) {
				floorRequestList.remove(i);
				break;
			}
		}
		
		request.setElevatorButtonMessageArray(elevatorRequestList);
		request.setFloorButtonMessageArray(floorRequestList);
	}
}
