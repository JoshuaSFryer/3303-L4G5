package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.ElevatorStateMessage;
import com.sysc3303.communication.FloorArrivalMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.commons.ElevatorVector;

/**
 * Handle the message from elevator
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class ElevatorRequestHandler extends RequestHandler implements Runnable {
	
	/**
	 * elevator message handler constructor
	 * @para request
	 * @para message
	 */
	public ElevatorRequestHandler(Request request, Message message, SchedulerMessageHandler schedulerMessageHandler) {
		this.request                 = request;
		this.message                 = message;   
		this.schedulerMessageHandler = schedulerMessageHandler;
	}

	public void run() {
		System.out.println("ElevatorRequestHandler starting..");
		System.out.println("Current requests: ");
		System.out.println(request.toString());
		
		if(message instanceof ElevatorStateMessage) {
			ElevatorStateMessage message          = (ElevatorStateMessage)this.message;
			ElevatorVector       elevatorVector   = message.getElevatorVector();
			int                  destinationFloor = elevatorVector.targetFloor;
			int                  elevatorId       = message.getElevatorId();
			int                  currentFloor     = elevatorVector.currentFloor;
			
			request.setElevatorVector(elevatorVector, elevatorId);
			
			System.out.println("Setting elevator vector: " + elevatorVector.toString());
			
			if(request.floorButtonMessagesContains(currentFloor) || request.containsTargetFloorInElevatorButtonMessages(elevatorId, currentFloor)) {
				System.out.println("Elevator arrived at destination!");
				
				removeTargetFloor(currentFloor, elevatorId);

				FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(destinationFloor, elevatorVector.currentDirection, elevatorId);
				
				System.out.println("Sending arrival message to floor");
				
				schedulerMessageHandler.sendFloorArrival(floorArrivalMessage);
			}	
			
			generateAndSendGoToFloorMessage();
		}
		else if(message instanceof ElevatorButtonMessage) {
			System.out.println("Recieved Elevator Button Message");
			
			sendGoToFloorMessageFromElevatorButtonMessage();
		}
	}
	
	private void sendGoToFloorMessageFromElevatorButtonMessage() {
		ElevatorButtonMessage message        = (ElevatorButtonMessage)this.message;
		int                   elevatorId     = message.getElevatorId();

		System.out.println(message.toString());
		
		request.addElevatorButtonMessage(message, elevatorId);
		
		int targetFloor = selectTargetFloor(elevatorId);
		
		if(targetFloor != -1) {
			GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor, elevatorId);
			
			System.out.println("Sending Go To Floor Message");
			System.out.println(goToFloorMessage.toString());
			
			schedulerMessageHandler.sendGoToFloor(goToFloorMessage);
		}
	}
	
	private int selectTargetFloor(int elevatorId) {
		ElevatorVector elevatorVector   = request.getElevatorVector(elevatorId);
		int   targetFloor               = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request, elevatorId);
		int   elevatorButtonTargetFloor = targetFloorDecider.selectTargetFloorFromElevatorButtonMessages(request.getElevatorButtonMessageArray(elevatorId), elevatorVector);
		int   closestTargetFloor        = 0;
		
		System.out.println("targetFloor in selectTargetFloor: " + targetFloor);
		System.out.println("elevatorButtonTargetFloor in selectTargetFloor: " + elevatorButtonTargetFloor);
	
		if(targetFloor != -1) {
			closestTargetFloor = targetFloorDecider.getNearestFloor(targetFloor, elevatorButtonTargetFloor, elevatorVector.currentFloor);
		}
		else {
			closestTargetFloor = elevatorButtonTargetFloor;
		}
		
		System.out.println("closestTargetFloor in selectTargetFloor: " + closestTargetFloor);
		return closestTargetFloor;
	}
	
	/**
	 * This function is to update the floorRequestList and elevatorRequestList
	 * once the elevator arrived a target floor, the target floor needs to be removed potentially
	 * both from floorRequestList and elevatorRequestList.
	 * 
	 * @para targetFloor
	 * @para targetDirection
	 */
	private void removeTargetFloor(int targetFloor, int elevatorId) {
		System.out.println("Removing target Floor: " + targetFloor);
		
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray(elevatorId);
		ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				System.out.println("Target found, removing from elevator request list...");
				elevatorRequestList.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.getFloor() == targetFloor) {
				System.out.println("Target found, removing from floor request list...");
				floorRequestList.remove(i);
				break;
			}
		}
		
		request.setElevatorButtonMessageArray(elevatorRequestList, elevatorId);
		request.setFloorButtonMessageArray(floorRequestList);
	}
}
