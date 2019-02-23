package com.sysc3303.scheduler;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;

/**
 * Abstract class for request handlers
 * @author Yu Yamanaka
 *
 */
public abstract class RequestHandler {
	protected final int               INVALID_FLOOR_1 = -1;
	protected Request                 request;
	protected SchedulerMessageHandler schedulerMessageHandler;
	protected Message                 message;
	protected TargetFloorDecider      targetFloorDecider = new TargetFloorDecider();
	protected Logger                  log                = Logger.getLogger(SchedulerMessageHandler.class);
	protected ElevatorStateMessageValidator elevatorStateMessageValidator = new ElevatorStateMessageValidator();

	/**
	 * generates and sends goToFloorMessage
	 * to all valid elevators considering all
	 * elevator button press and floor requests
	 * @throws InterruptedException 
	 */
	protected void generateAndSendGoToFloorMessage() {
		if(request.generatorIsOn()) {
			return;
		}
		request.setGeneratorOn();
		while(!request.floorButtonMessagesIsEmpty() || !request.elevatorButtonMessagesIsEmpty()) {
			TargetWithDirection[] targetFloorsFromFloorButtonMessages    = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request);
			int[]                 targetFloorsFromElevatorButtonMessages = targetFloorDecider.selectFloorFromAllElevatorsElevatorButtonMessage(request);
			int                   numberOfElevator                       = request.getNumberOfElevator();
			
			if(hasOnlyInvalidFloor(targetFloorsFromFloorButtonMessages) && hasOnlyInvalidFloor(targetFloorsFromElevatorButtonMessages)) {
				request.resetTargetDirection();
				continue;
			}
			log.debug("Printing target floor buttons for each elevator");
			for(int i = 0; i < targetFloorsFromFloorButtonMessages.length; i++) {
				log.debug(targetFloorsFromFloorButtonMessages[i].getTargetFloor());
			}
			
			for(int i = 0; i < targetFloorsFromElevatorButtonMessages.length; i++) {
				log.debug(targetFloorsFromElevatorButtonMessages[i]);
			}
			
			for(int i = 0; i < numberOfElevator; i++) {
				Direction           curTargetDirection     = targetFloorsFromFloorButtonMessages[i].getTargetDirection();
				int                 targetFromFloorButton  = targetFloorsFromFloorButtonMessages[i].getTargetFloor();
				int                 currentFloor           = request.getElevatorVector(i).currentFloor;
				int                 targetFloor            = targetFloorDecider.getNearestFloor(targetFromFloorButton, 
						                                                                         targetFloorsFromElevatorButtonMessages[i], currentFloor);
				if(targetFloor != -1 && targetFloor != 0) {
					ElevatorVector curElevatorVector = request.getElevatorVector(i);
					ElevatorVector elevatorVector    = new ElevatorVector(curElevatorVector.currentFloor, curElevatorVector.currentDirection, targetFloor);
					request.setElevatorVector(elevatorVector, i);
					
					log.debug("printing prps...");
					log.debug(curTargetDirection);
					log.debug(targetFromFloorButton);
					log.debug(currentFloor);
					log.debug(targetFloor);
					
					if(targetFloor == targetFromFloorButton) {
						log.debug("Setting target direction for elevator " + i);
						request.setTargetDirection(curTargetDirection, i);
					}
					
					schedulerMessageHandler.sendGoToFloor(new GoToFloorMessage(targetFloor, i));
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setGeneratorOff();
	}
	
	protected boolean hasOnlyInvalidFloor(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != INVALID_FLOOR_1) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean hasOnlyInvalidFloor(TargetWithDirection[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i].getTargetFloor() != INVALID_FLOOR_1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function is to update the floorRequestList and elevatorRequestList
	 * once the elevator arrived a target floor, the target floor needs to be removed potentially
	 * both from floorRequestList and elevatorRequestList.
	 * 
	 * @para targetFloor
	 * @para targetDirection
	 */
	protected void removeTargetFloor(int targetFloor, int elevatorId, Direction targetDirection) {	
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray(elevatorId);
		ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			log.debug("cur size: " + elevatorRequestList.size());
			log.debug("cur index: " + i);
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				log.debug("removing... " + elevatorRequestList.get(i));
				elevatorRequestList.set(i, null);
			}
		}
		
		elevatorRequestList.removeAll(Collections.singleton(null));
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);

			log.debug(curFloorRequest);
			if(curFloorRequest.getFloor() == targetFloor && curFloorRequest.getDirection() == targetDirection) {
				log.debug("removing... " + curFloorRequest);
				floorRequestList.set(i, null);
			}
		}
		
		floorRequestList.removeAll(Collections.singleton(null));
	}
}
