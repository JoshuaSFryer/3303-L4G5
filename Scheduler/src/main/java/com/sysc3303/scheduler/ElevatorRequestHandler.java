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
		log.debug("ElevatorRequestHandler starting");
		
		if(message instanceof ElevatorStateMessage) {
			ElevatorStateMessage message          = (ElevatorStateMessage)this.message;
			ElevatorVector       elevatorVector   = message.getElevatorVector();
			int                  destinationFloor = elevatorVector.targetFloor;
			int                  elevatorId       = message.getElevatorId();
			int                  currentFloor     = elevatorVector.currentFloor;
			
			request.setElevatorVector(elevatorVector, elevatorId);
			
			log.debug(request);
			
			if(currentFloor == destinationFloor) {
				log.info("Elevator " + elevatorId + " arrived at destination");
				
				removeTargetFloor(currentFloor, elevatorId);
				
				ElevatorVector      elevatorVectorResetTargetFloor = new ElevatorVector(currentFloor, elevatorVector.currentDirection, 0);
				FloorArrivalMessage floorArrivalMessage            = new FloorArrivalMessage(destinationFloor, elevatorVector.currentDirection, elevatorId);
				
				request.setElevatorVector(elevatorVectorResetTargetFloor, elevatorId);
				schedulerMessageHandler.sendFloorArrival(floorArrivalMessage);
			}	
			
			generateAndSendGoToFloorMessage();
		}
		else if(message instanceof ElevatorButtonMessage) {
			sendGoToFloorMessageFromElevatorButtonMessage();
		}
	}
	
	/**
	 * Generates and Sends GoToFloorMessage considering
	 * elevator button message and floor button message arrays 
	 * of particular elevator
	 */
	private void sendGoToFloorMessageFromElevatorButtonMessage() {
		ElevatorButtonMessage message        = (ElevatorButtonMessage)this.message;
		int                   elevatorId     = message.getElevatorId();
		
		request.addElevatorButtonMessage(message, elevatorId);
		
		int targetFloor = selectTargetFloor(elevatorId);
		
		if(targetFloor != INVALID_FLOOR) {
			ElevatorVector curElevatorVector = request.getElevatorVector(elevatorId);
			ElevatorVector elevatorVector    = new ElevatorVector(curElevatorVector.currentFloor, curElevatorVector.currentDirection, targetFloor);
			
			request.setElevatorVector(elevatorVector, elevatorId);
			schedulerMessageHandler.sendGoToFloor(new GoToFloorMessage(targetFloor, elevatorId));
		}
	}
	
	/**
	 * selects target floor considering
	 * elevator button message and floor button message
	 * of particular elevator
	 * 
	 * @param elevatorId
	 * @return
	 */
	private int selectTargetFloor(int elevatorId) {
		ElevatorVector elevatorVector   = request.getElevatorVector(elevatorId);
		int   targetFloor               = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request, elevatorId);
		int   elevatorButtonTargetFloor = targetFloorDecider.selectTargetFloorFromElevatorButtonMessages(request.getElevatorButtonMessageArray(elevatorId), elevatorVector);
		int   closestTargetFloor        = 0;
	
		if(targetFloor != INVALID_FLOOR) {
			closestTargetFloor = targetFloorDecider.getNearestFloor(targetFloor, elevatorButtonTargetFloor, elevatorVector.currentFloor);
		}
		else {
			closestTargetFloor = elevatorButtonTargetFloor;
		}
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
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray(elevatorId);
		ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				elevatorRequestList.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.getFloor() == targetFloor) {
				floorRequestList.remove(i);
				break;
			}
		}
	}
}
