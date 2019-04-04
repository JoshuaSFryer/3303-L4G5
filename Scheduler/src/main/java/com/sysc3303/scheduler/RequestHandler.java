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
 * @author Xinrui Zhang
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
				if(request.everyElevatorArrived()) {
					request.resetTargetDirection();
					continue;
				}
			}
			
			for(int i = 0; i < numberOfElevator; i++) {
				Direction           curTargetDirection     = targetFloorsFromFloorButtonMessages[i].getTargetDirection();
				int                 targetFromFloorButton  = targetFloorsFromFloorButtonMessages[i].getTargetFloor();
				int                 currentFloor           = request.getElevatorVector(i).currentFloor;
				int                 targetFloor            = targetFloorDecider.getNearestFloor(targetFromFloorButton, 
						                                                                         targetFloorsFromElevatorButtonMessages[i], currentFloor);
				if(targetFloor != -1) {
					ElevatorVector curElevatorVector = request.getElevatorVector(i);
					Direction      direction         = Direction.IDLE;
					int            curFloor          = curElevatorVector.currentFloor;
					
					if(curFloor < targetFloor) {
						direction = Direction.UP;
					}
					else if(curFloor > targetFloor) {
						direction = Direction.DOWN;
					}
					
					ElevatorVector elevatorVector = new ElevatorVector(curFloor, direction, targetFloor);
					request.setElevatorVector(elevatorVector, i);
					
					if(targetFloor == targetFromFloorButton) {
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
	
	/**
	 * This function returns true if the arr has only invalid floor,
	 * reutrn false otherwise. Initial version.
	 *
	 * @para arr
	 * @return boolean
	 */
	protected boolean hasOnlyInvalidFloor(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != INVALID_FLOOR_1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function returns true if the arr has only invalid floor,
	 * reutrn false otherwise.
	 *
	 * @para arr
	 * @return boolean
	 */
	protected boolean (TargetWithDirection[] arr) {
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
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				elevatorRequestList.set(i, null);
			}
		}
		
		elevatorRequestList.removeAll(Collections.singleton(null));
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);

			if(curFloorRequest.getFloor() == targetFloor && curFloorRequest.getDirection() == targetDirection) {
				floorRequestList.set(i, null);
			}
		}
		
		floorRequestList.removeAll(Collections.singleton(null));
	}
}
