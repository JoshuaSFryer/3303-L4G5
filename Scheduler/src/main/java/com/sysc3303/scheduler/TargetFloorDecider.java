package com.sysc3303.scheduler;

import java.util.ArrayList;


import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

/**
 * Selects target floors for
 * elevator
 * @version 1.2
 * @author Yu Yamanaka
 * @version 1.1
 * @author Xinrui Zhang
 */
public class TargetFloorDecider {
	TargetFloorValidator targetFloorValidator = new TargetFloorValidator();
	
	/**
	 * Selects target floor candidates
	 * for each elevator
	 * @param numberOfElevator
	 * @param request
	 * @return
	 */
	private ArrayList<ArrayList<TargetWithDirection>> selectTargetFloorCandidates(int numberOfElevator, Request request) {
		ArrayList<ArrayList<TargetWithDirection>> targetFloorCandidates = new ArrayList<ArrayList<TargetWithDirection>>();
		ArrayList<FloorButtonMessage>             floorButtonMessages   = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < numberOfElevator; i++) {
			targetFloorCandidates.add(null);
		}
		
		for(int i = 0; i < floorButtonMessages.size(); i++) {
			addValidTargetFloor(numberOfElevator, request, targetFloorCandidates,floorButtonMessages.get(i));
		}

		return targetFloorCandidates;
	}
	
	/**
	 * Add valid target floor
	 * for each elevator
	 * @param numberOfElevator
	 * @param request
	 * @param targetFloorCandidates
	 * @param message
	 * @return
	 */
	private void addValidTargetFloor(int numberOfElevator, Request request, ArrayList<ArrayList<TargetWithDirection>> targetFloorCandidates, FloorButtonMessage message) {
		if(message == null) {
			return;
		}
		
		int      buttonTargetFloor = message.getFloor();
		Direction buttonDirection  = message.getDirection();
		
		for(int j = 0; j < numberOfElevator; j++) {
			Direction targetDirection = request.getTargetDirection(j);
			
			if(targetFloorValidator.validTargetFloor(buttonTargetFloor, buttonDirection, request.getElevatorVector(j), targetDirection)
					&& !request.elevatorIsStuck(j)) {
				if(targetFloorCandidates.get(j) == null) {
					targetFloorCandidates.set(j, new ArrayList<TargetWithDirection>());
				}
				targetFloorCandidates.get(j).add(new TargetWithDirection(buttonTargetFloor, buttonDirection));
			}
		}
	}
	
	/**
	 * selects target floor for
	 * all elevators elevator button message
	 * @param request
	 * @return
	 */
	public int[] selectFloorFromAllElevatorsElevatorButtonMessage(Request request) {
		int numberOfElevator = request.getNumberOfElevator();
		int[] targetFloors   = new int[numberOfElevator];
		
		for(int i = 0; i < numberOfElevator; i++) {
			if(request.elevatorIsStuck(i)) {
				targetFloors[i] = -1;
			}
			else {
				targetFloors[i] = selectTargetFloorFromElevatorButtonMessages(request.getElevatorButtonMessageArray(i), request.getElevatorVector(i));
			}
		}
		
		return targetFloors;
	}
	
	/**
	 * Selects target floor from target
	 * floor candidates
	 * @param numberOfElevator
	 * @param targetFloorCandidates
	 * @param request
	 * @return
	 */
	public TargetWithDirection[] selectTargetFloorFromCandidates(int numberOfElevator, ArrayList<ArrayList<TargetWithDirection>> targetFloorCandidates, Request request) {
		TargetWithDirection[] targetFloors = new TargetWithDirection[numberOfElevator];
	
		if(targetFloorCandidates.isEmpty()) {
			return null;
		}
		
		for(int i = 0; i < targetFloorCandidates.size(); i++) {
			targetFloors[i] = new TargetWithDirection(-1, Direction.IDLE);
			
			if(targetFloorCandidates.get(i) == null) {
				continue;
			}
			
			TargetWithDirection nearestFloor       = getNearestTargetWithDirection(targetFloorCandidates.get(i), request.getElevatorVector(i).currentFloor);
			int                 nearestTargetFloor = nearestFloor.getTargetFloor();
			
			if(nearestTargetFloor == -1 || elevatorIsOnItsWay(nearestTargetFloor, request)) {
				continue;
			}
			
			
			if(containsInt(targetFloors, i, nearestTargetFloor)) {
				int duplicateElevatorId = getDuplicateIndex(targetFloors, nearestFloor.getTargetFloor());
 				int closestElevatorId   = getClosestElevatorIdFromNearestFloor(i, duplicateElevatorId, nearestFloor.getTargetFloor(), request);
 				
 				if(closestElevatorId != i) {
 					continue;
 				}
 				targetFloors[duplicateElevatorId] = new TargetWithDirection(-1, Direction.IDLE);
			}
			targetFloors[i] = nearestFloor;
		}
		return targetFloors;
	}
	
	/**
	 * Selects target considering
	 * floor button messages for particular
	 * elevator
	 * @param request
	 * @param elevatorId
	 * @return
	 */
	public TargetWithDirection selectTargetFloorFromFloorButtonMessages(Request request, int elevatorId) {
		TargetWithDirection[] targetFloors = selectTargetFloorFromFloorButtonMessages(request);
		
		return targetFloors[elevatorId];
	}
	
	/**
	 * selects target floor considering
	 * floor button messages for all
	 * elevators
	 * @param request
	 * @return
	 */
	public TargetWithDirection[] selectTargetFloorFromFloorButtonMessages(Request request) {
		int                                       numberOfElevator      = request.getNumberOfElevator();
		ArrayList<ArrayList<TargetWithDirection>> targetFloorCandidates = selectTargetFloorCandidates(numberOfElevator, request);
		TargetWithDirection[]                     targetFloors          = selectTargetFloorFromCandidates(numberOfElevator, targetFloorCandidates, request);
		
		return targetFloors;
	}
	
	/**
	 * Selects target floor from elevator button
	 * messages of single elevator
	 * @param elevatorButtonMessageArr
	 * @param elevatorVector
	 * @return
	 */
	public int selectTargetFloorFromElevatorButtonMessages(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr, ElevatorVector elevatorVector) {
		ArrayList<Integer> targetFloorCandidates = new ArrayList<Integer>();
		
		for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
			ElevatorButtonMessage elevatorButtonMessage = elevatorButtonMessageArr.get(i);
			int                   destinationFloor      = elevatorButtonMessage.getDestinationFloor();
			
			if(targetFloorValidator.validTargetFloor(destinationFloor, elevatorVector) && elevatorVector.targetFloor != destinationFloor) {
				targetFloorCandidates.add(destinationFloor);
			}
		}
		
		int targetFloor = getNearestFloor(targetFloorCandidates, elevatorVector.currentFloor);
		
		return targetFloor;
	}
	
	/**
	 * checks if any other elevator
	 * is on its way to targetFloor
	 * @param targetFloor
	 * @param request
	 * @return
	 */
	private boolean elevatorIsOnItsWay(int targetFloor, Request request) {
		int numberOfElevator = request.getNumberOfElevator();
		
		for(int i = 0; i < numberOfElevator; i++) {
			ElevatorVector elevatorVector = request.getElevatorVector(i);
			
			if(elevatorVector.targetFloor == targetFloor) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns closest elevatorId from
	 * nearestFloor
	 * @param firstElevatorId
	 * @param secondElevatorId
	 * @param nearestFloor
	 * @param request
	 * @return
	 */
	private int getClosestElevatorIdFromNearestFloor(int firstElevatorId, int secondElevatorId, int nearestFloor, Request request) {
		ElevatorVector firstElevatorVector     = request.getElevatorVector(firstElevatorId);
		ElevatorVector secondElevatorVector    = request.getElevatorVector(secondElevatorId);
		int            firstElevatorFloorDiff  = Math.abs(firstElevatorVector.currentFloor - nearestFloor);
		int            secondElevatorFloorDiff = Math.abs(secondElevatorVector.currentFloor - nearestFloor);
		
		if(firstElevatorFloorDiff < secondElevatorFloorDiff) {
			return firstElevatorId;
		}
		
		return secondElevatorId;
	}
	
	/**
	 * checks if nearest floor contained
	 * in targetFloor, returns that index
	 * @param targetFloors
	 * @param nearestFloor
	 * @return
	 */
	private int getDuplicateIndex(TargetWithDirection[] targetFloors, int nearestFloor) {
		int duplicatedIndex = -1;
		
		for(int i = 0; i < targetFloors.length; i++) {
			if(targetFloors[i].getTargetFloor() == nearestFloor) {
				duplicatedIndex = i;
				break;
			}
		}
		return duplicatedIndex;
	}
	
	/**
	 * checks if number is contained in array,
	 * returns true if it does
	 * @param array
	 * @param number
	 * @return
	 */
	private boolean containsInt(TargetWithDirection[] array, int len, int number) {
		for(int i = 0; i < len; i++) {
			if(array[i].getTargetFloor() == number) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets nearest floor from currentFloor to targetFloorCandidates
	 * @param targetFloorCandidates
	 * @param currentFloor
	 * @return
	 */
	private int getNearestFloor(ArrayList<Integer> targetFloorCandidates, int currentFloor) {
		int difference   = 0;
		int nearestFloor = -1;
		
		for(int i = 0; i < targetFloorCandidates.size(); i++) {
			int curFloor      = targetFloorCandidates.get(i);
			int curDifference = Math.abs(curFloor - currentFloor);
			
			if(i == 0 || curDifference < difference) {
				difference   = curDifference;
				nearestFloor = curFloor;
			}
		}
		
		return nearestFloor;
	}
	
	/**
	 * gets nearest floor from currentFloor to targetFloorCandidates
	 * @param targetFloorCandidates
	 * @param currentFloor
	 * @return
	 */
	private TargetWithDirection getNearestTargetWithDirection(ArrayList<TargetWithDirection> targetFloorCandidates, int currentFloor) {
		int difference                   = 0;
		TargetWithDirection nearestFloor = new TargetWithDirection(-1, Direction.IDLE);
		
		for(int i = 0; i < targetFloorCandidates.size(); i++) {
			int curFloor      = targetFloorCandidates.get(i).getTargetFloor();
			int curDifference = Math.abs(curFloor - currentFloor);
			
			if(i == 0 || curDifference < difference) {
				difference   = curDifference;
				nearestFloor = targetFloorCandidates.get(i);
			}
		}
		
		return nearestFloor;
	}
	
	/**
	 * checks if first or second floor
	 * is nearest to current floor, 
	 * returns any one of closest floor
	 * @param firstFloor
	 * @param secondFloor
	 * @param currentFloor
	 * @return
	 */
	public int getNearestFloor(int firstFloor, int secondFloor, int currentFloor) {
		if(firstFloor == -1 && secondFloor == -1) {
			return -1;
		}
		if(firstFloor == -1) {
			return secondFloor;
		}
		else if(secondFloor == -1) {
			return firstFloor;
		}
		
		int firstFloorDiff  = Math.abs(firstFloor - currentFloor);
		int secondFloorDiff = Math.abs(secondFloor - currentFloor);
		
		if(firstFloorDiff < secondFloorDiff) {
			return firstFloor;
		}
		return secondFloor;
	}
}
