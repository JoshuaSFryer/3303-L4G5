package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

public class TargetFloorDecider {
	TargetFloorValidator targetFloorValidator = new TargetFloorValidator();
	
	private ArrayList<ArrayList<Integer>> selectTargetFloorCandidates(int numberOfElevator, Request request) {
		ArrayList<ArrayList<Integer>> targetFloorCandidates = new ArrayList<ArrayList<Integer>>();
		ArrayList<FloorButtonMessage> floorButtonMessages   = (ArrayList<FloorButtonMessage>)request.getFloorButtonMessageArray();
		
		for(int i = 0; i < numberOfElevator; i++) {
			targetFloorCandidates.add(null);
		}
		
		for(int i = 0; i < floorButtonMessages.size(); i++) {
			int      curTargetFloor = floorButtonMessages.get(i).getFloor();
			Direction curDirection  = floorButtonMessages.get(i).getDirection();
			
			for(int j = 0; j < numberOfElevator; j++) {
				if(targetFloorValidator.validTargetFloor(curTargetFloor, curDirection, request.getElevatorVector(j))) {
					if(targetFloorCandidates.get(j) == null) {
						targetFloorCandidates.set(j, new ArrayList<Integer>());
					}
					targetFloorCandidates.get(j).add(curTargetFloor);
				}
			}
		}
		
		return targetFloorCandidates;
	}
	
	public int[] selectFloorFromAllElevatorsElevatorButtonMessage(Request request) {
		int numberOfElevator = request.getNumberOfElevator();
		int[] targetFloors   = new int[numberOfElevator];
		
		for(int i = 0; i < numberOfElevator; i++) {
			targetFloors[i] = selectTargetFloorFromElevatorButtonMessages(request.getElevatorButtonMessageArray(i), request.getElevatorVector(i));
		}
		
		return targetFloors;
	}
	
	public int[] selectTargetFloorFromCandidates(int numberOfElevator, ArrayList<ArrayList<Integer>> targetFloorCandidates, Request request) {
		int[] targetFloors = new int[numberOfElevator];
	
		if(targetFloorCandidates.isEmpty()) {
			return null;
		}
		
		for(int i = 0; i < targetFloorCandidates.size(); i++) {
			targetFloors[i] = -1;
			
			if(targetFloorCandidates.get(i) == null) {
				continue;
			}
			
			int nearestFloor = getNearestFloor(targetFloorCandidates.get(i), request.getElevatorVector(i).currentFloor);
			
			if(nearestFloor == -1 || elevatorIsOnItsWay(nearestFloor, request)) {
				continue;
			}
			
			if(containsInt(targetFloors, nearestFloor)) {
				int duplicateElevatorId = getDuplicateIndex(targetFloors, nearestFloor);
 				int closestElevatorId   = getClosestElevatorIdFromNearestFloor(i, duplicateElevatorId, nearestFloor, request);
 				
 				if(closestElevatorId != i) {
 					continue;
 				}
 				targetFloors[duplicateElevatorId] = -1;
			}
			
			targetFloors[i] = nearestFloor;
		}
		
		return targetFloors;
	}
	
	public int selectTargetFloorFromFloorButtonMessages(Request request, int elevatorId) {
		int[] targetFloors = selectTargetFloorFromFloorButtonMessages(request);
		
		return targetFloors[elevatorId];
	}
	
	public int[] selectTargetFloorFromFloorButtonMessages(Request request) {
		int                           numberOfElevator      = request.getNumberOfElevator();
		ArrayList<ArrayList<Integer>> targetFloorCandidates = selectTargetFloorCandidates(numberOfElevator, request);
		int[]                         targetFloors          = selectTargetFloorFromCandidates(numberOfElevator, targetFloorCandidates, request);
				
		return targetFloors;
	}
	
	public int selectTargetFloorFromElevatorButtonMessages(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr, ElevatorVector elevatorVector) {
		ArrayList<Integer> targetFloorCandidates = new ArrayList<Integer>();
		
		for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
			ElevatorButtonMessage elevatorButtonMessage = elevatorButtonMessageArr.get(i);
			int                   destinationFloor      = elevatorButtonMessage.getDestinationFloor();
			
			if(targetFloorValidator.validTargetFloor(destinationFloor, elevatorVector)) {
				targetFloorCandidates.add(destinationFloor);
			}
		}
		
		int targetFloor = getNearestFloor(targetFloorCandidates, elevatorVector.currentFloor);
		
		return targetFloor;
	}
	
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
	
	private int getDuplicateIndex(int[] targetFloors, int nearestFloor) {
		int duplicatedIndex = -1;
		
		for(int i = 0; i < targetFloors.length; i++) {
			if(targetFloors[i] == nearestFloor) {
				duplicatedIndex = i;
				break;
			}
		}
		return duplicatedIndex;
	}
	
	private boolean containsInt(int[] array, int number) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == number) {
				return true;
			}
		}
		return false;
	}
	
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
			return firstFloorDiff;
		}
		return secondFloorDiff;
	}
}
