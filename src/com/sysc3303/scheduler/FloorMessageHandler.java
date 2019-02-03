package com.sysc3303.scheduler;

import java.util.ArrayList;
import java.util.Date;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorButtonMessage;
import com.sysc3303.commons.FloorButtonMessage;
import com.sysc3303.commons.GoToFloorMessage;
import com.sysc3303.elevator.ElevatorVector;

/**
 * @author Yu Yamanaka Xinrui Zhang
 * 
 * Handles message from floor
 */
public class FloorMessageHandler implements Runnable {
	private Request            request;
	private int                targetFloor;
	private FloorButtonMessage message;
	private GoToFloorMessage   goToFloorMessage;
	
	/**
	 * @param request
	 * @param message
	 */
	public FloorMessageHandler(Request request, FloorButtonMessage message) {
		this.request = request;
		this.message = message;
	}
	
	public void run() {
		request.addFloorButtonMessage(message);
			
		targetFloor      = decideTargetFloor();
		goToFloorMessage = new GoToFloorMessage(targetFloor);
	}
	
	/**
	 * @return GoToFloorMessage
	 */
	public GoToFloorMessage getGoToFloorMessage() {
		return goToFloorMessage;
	}
	
	/**
	 * Decides target floor and returns it
	 * @return int
	 */
	private int decideTargetFloor() {
		ElevatorVector elevatorVector = request.getElevatorVector();
		Direction      elevatorStatus = elevatorVector.currentDirection;
		
		int target = -1;
		if(elevatorStatus == Direction.IDLE) {
			// want to set to null, this may need to be changed
			FloorButtonMessage    floorRequest    = getEariestFloorRequest();
			ElevatorButtonMessage elevatorRequest = getEariestElevatorRequest();
			
			//NOT SURE what the objects are called!!!!!!!!!!!!!!!!!!!!
			if(floorRequest.getTime().compareTo(elevatorRequest.getTime()) > 0) {
				target = elevatorRequest.getDestinationFloor();
			}
			else {
				target = floorRequest.getFloor();
			}
		}
		else if(elevatorStatus == Direction.UP) {
			ArrayList<FloorButtonMessage>    selectFloorListup    = getFloorUpRequestArray();
			ArrayList<ElevatorButtonMessage> selectElevatorListup = getElevatorUpRequestArray();

			//inefficient sorting algothrim
			//go through the selectFloorList to find the nearest request
			target = getNearestUpRequest(selectFloorListup, selectElevatorListup);
		}
		else {
			ArrayList<FloorButtonMessage>    selectFloorListDown    = getFloorDownRequestArray();
			ArrayList<ElevatorButtonMessage> selectElevatorListDown = getElevatorDownRequestArray();

			//inefficient sorting algothrim
			//go through the selectFloorList to find the nearest request
			target = getNearestDownRequest(selectFloorListDown, selectElevatorListDown);
		}
		return target;
	}
	
	/**
	 * gets earliest FloorButtonMessage in time, from request 
	 * floorButtonMessage array 
	 * 
	 * @return
	 */
	private FloorButtonMessage getEariestFloorRequest() {
		FloorButtonMessage floorRequest = null;
		ArrayList<FloorButtonMessage> floorRequestList = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			Date               curRequestTime  = curFloorRequest.getTime();
			
			if(floorRequest == null || floorRequest.getTime().compareTo(curRequestTime) > 0){
				floorRequest = curFloorRequest;
			}
		}
		
		return floorRequest;
	}
	
	/**
	 * gets earliest ElevatorButtonMessage in time, from 
	 * request elevatorButtonMessage array
	 * 
	 * @return ElevatorButtonMessage
	 */
	private ElevatorButtonMessage getEariestElevatorRequest() {
		ElevatorButtonMessage elevatorRequest = null;
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray();
		
		//go through the elevatorRequestList to find the earliest request
		//not sure what time is being named in elevator????????????????????????
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorButtonMessage curElevatorRequest = elevatorRequestList.get(i);
			Date                  curRequestTime     = curElevatorRequest.getTime();
			
			if(elevatorRequest == null || elevatorRequest.getTime().compareTo(curRequestTime) > 0) {
				elevatorRequest = curElevatorRequest;
			}
		}
		return elevatorRequest;
	}
	
	/**
	 * gets list of up direction request from FloorButtonMessage
	 * array in request class
	 * 
	 * @return ArrayList<FloorButtonMessage>
	 */
	private ArrayList<FloorButtonMessage> getFloorUpRequestArray() {
		ArrayList<FloorButtonMessage> selectFloorListup = new ArrayList<FloorButtonMessage>();
		ArrayList<FloorButtonMessage> floorRequestList  = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.getDirection() == Direction.UP && 
			   curFloorRequest.getFloor() > request.getElevatorVector().currentFloor) {//???
				selectFloorListup.add(curFloorRequest);
			}
		}
		
		return selectFloorListup;
	}
	
	/**
	 * gets list of elevatorButtonMessage that has up direction
	 * from from request class
	 * 
	 * @return ArrayList<ElevatorButtonMessage>
	 */
	private ArrayList<ElevatorButtonMessage> getElevatorUpRequestArray() {
		ArrayList<ElevatorButtonMessage> selectElevatorListup = new ArrayList<ElevatorButtonMessage>();
		ArrayList<ElevatorButtonMessage> elevatorRequestList  = request.getElevatorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorButtonMessage curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.getDestinationFloor() > request.getElevatorVector().currentFloor){//???
					selectElevatorListup.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListup;
	}
	
	/**
	 * 
	 * gets list of FloorButtonMessage that has down direction
	 * from request class
	 * 
	 * @return ArrayList<FloorButtonMessage>
	 */
	private ArrayList<FloorButtonMessage> getFloorDownRequestArray() {
		ArrayList<FloorButtonMessage> selectFloorListDown = new ArrayList<FloorButtonMessage>();
		ArrayList<FloorButtonMessage> floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			if(curFloorRequest.getDirection() == Direction.DOWN && 
			   curFloorRequest.getFloor() < request.getElevatorVector().currentFloor){//???
				selectFloorListDown.add(curFloorRequest);
			}
		}
		
		return selectFloorListDown;
	}
	
	/**
	 * gets list of ElevatorButtonMessage that has down direction
	 * from request class
	 * 
	 * @return
	 */
	private ArrayList<ElevatorButtonMessage> getElevatorDownRequestArray() {
		ArrayList<ElevatorButtonMessage> selectElevatorListDown = new ArrayList<ElevatorButtonMessage>();
		ArrayList<ElevatorButtonMessage> elevatorButtonMessageList = request.getElevatorButtonMessageArray();
		
		for(int i = 0; i < elevatorButtonMessageList.size(); i++) {
			ElevatorButtonMessage curElevatorButtonMessage = elevatorButtonMessageList.get(i);
			
			if(curElevatorButtonMessage.getDestinationFloor() < request.getElevatorVector().currentFloor){//???
				selectElevatorListDown.add(curElevatorButtonMessage);
			}
		}
		
		return selectElevatorListDown;
	}
	
	/**
	 * gets nearest targetDistination from FloorButtonMessage array
	 * and ElevatorButtonMessage array in parameter, that has up direction
	 * 
	 * @param selectFloorListup
	 * @param selectElevatorListup
	 * @return int
	 */
	private int getNearestUpRequest(ArrayList<FloorButtonMessage> selectFloorListup, ArrayList<ElevatorButtonMessage> selectElevatorListup) {
		int targetDistinationFromFloor    = 0;
		int targetDistinationFromElevator = 0;
		
		for(int i = 0 ; i < selectFloorListup.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int curRequestFloor = selectFloorListup.get(i).getFloor();
			
			if(targetDistinationFromFloor == 0 || targetDistinationFromFloor > curRequestFloor){
				targetDistinationFromFloor = curRequestFloor;//???
			}
		}

		for(int i = 0; i < selectElevatorListup.size(); i++) {
			int curRequestFloor = selectElevatorListup.get(i).getDestinationFloor();
			if(targetDistinationFromElevator == 0 || targetDistinationFromElevator > curRequestFloor) {
				targetDistinationFromElevator = curRequestFloor;//???
			}
		}
		
		if(targetDistinationFromFloor < targetDistinationFromElevator) {
			return targetDistinationFromFloor;
		}
		
		return targetDistinationFromElevator;
	}
	
	/**
	 * gets nearest targetDistination from FloorButtonMessage array
	 * and ElevatorButtonMessage array in parameter, that has down direction
	 * 
	 * @param selectFloorListDown
	 * @param selectElevatorListDown
	 * @return int
	 */
	private int getNearestDownRequest(ArrayList<FloorButtonMessage> selectFloorListDown, ArrayList<ElevatorButtonMessage> selectElevatorListDown) {
		int targetDistinationFromFloor    = 0;
		int targetDistinationFromElevator = 0;
		
		for(int i = 0 ;i < selectFloorListDown.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int requestFloor = selectFloorListDown.get(i).getFloor();
			
			if(targetDistinationFromFloor == 0 || targetDistinationFromFloor < requestFloor){
				targetDistinationFromFloor = requestFloor;//???
			}
		}

		for(int i = 0 ; i < selectElevatorListDown.size(); i++) {
			int requestFloor = selectElevatorListDown.get(i).getDestinationFloor();
			
			if(targetDistinationFromElevator == 0 || targetDistinationFromElevator < requestFloor){
				targetDistinationFromElevator = requestFloor;//???
			}
		}
		
		if(targetDistinationFromFloor < targetDistinationFromElevator) {
			return targetDistinationFromFloor;
		}
		
		return targetDistinationFromElevator;
	}
}
