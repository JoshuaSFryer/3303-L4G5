package com.sysc3303.scheduler;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

public class FloorMessageHandler extends Thread {
	private Request              request;
	private MessageHandler       messageHandler;
	private SchedulerElevatorStatus       elevatorStatus;
	private InetAddress          elevatorAddress;
	private int                  elevatorPort;
	private CommunicationHandler communicationHandler;
	private int                  targetFloor;
	
	public FloorMessageHandler(Request request, MessageHandler messageHandler, SchedulerElevatorStatus elevatorStatus, 
			                   int elevatorPort, InetAddress elevatorAddress, CommunicationHandler communicationHandler) {
		this.request              = request;
		this.messageHandler       = messageHandler;
		this.elevatorStatus       = elevatorStatus;
		this.elevatorPort         = elevatorPort;
		this.elevatorAddress      = elevatorAddress;
		this.communicationHandler = communicationHandler;
	}
	
	public void run() {
		boolean running = true;
		
		while(running) {
			if(!messageHandler.recievedFloorButtonMessage) {
				continue;
			}
			request.addFloorButtonMessage(messageHandler.getFloorButtonMessage());
			
			targetFloor                       = decideTargetFloor();
			GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor);
			
			communicationHandler.send(goToFloorMessage, elevatorAddress, elevatorPort);
		}
	}
	
	private int decideTargetFloor() {
		int target = -1;
		if(elevatorStatus == SchedulerElevatorStatus.Stationary) {
			// want to set to null, this may need to be changed
			FloorButtonMessage    floorRequest    = getEariestFloorRequest();
			ElevatorButtonMessage elevatorRequest = getEariestElevatorRequest();
			
			//NOT SURE what the objects are called!!!!!!!!!!!!!!!!!!!!
			if(floorRequest.time.compareTo(elevatorRequest.time) > 0) {
				target = elevatorRequest.numberFromElevator;
			}
			else {
				target = floorRequest.numberFromFloor;
			}
		}
		else if(elevatorStatus == SchedulerElevatorStatus.GoingUp) {
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
		return target;
	}
	
	private FloorRequestButtonMessage getEariestFloorRequest() {
		FloorButtonMesssage floorRequest;
		ArrayList<FloorButtonMessage> floorRequestList = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			Date               curRequestTime  = curFloorRequest.time;
			
			if(floorRequest == null || floorRequest.time.compareTo(curRequestTime) > 0){
				floorRequest = curFloorRequest;
			}
		}
		
		return floorRequest;
	}
	
	private ElevatorButtonMessage getEariestElevatorRequest() {
		ElevatorButtonMessage elevatorRequest;
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray();
		
		//go through the elevatorRequestList to find the earliest request
		//not sure what time is being named in elevator????????????????????????
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorButtonMessage curElevatorRequest = elevatorRequestList.get(i);
			Date                  curRequestTime     = curElevatorRequest.time;
			
			if(elevatorRequest == null || elevatorRequest.time.compareTo(curRequestTime) > 0) {
				elevatorRequest = curElevatorRequest;
			}
		}
		return elevatorRequest;
	}
	
	private ArrayList<FloorButtonMessage> getFloorUpRequestArray() {
		ArrayList<FloorButtonMessage> selectFloorListup = new ArrayList<FloorButtonMessage>();
		ArrayList<FloorButtonMessage> floorRequestList  = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.direction == requestUp && 
			   curFloorRequest.requestFloor > elevatorPosition) {//???
				selectFloorListup.add(curFloorRequest);
			}
		}
		
		return selectFloorListup;
	}
	
	private ArrayList<ElevatorButtonMessage> getElevatorUpRequestArray() {
		ArrayList<ElevatorButtonMessage> selectElevatorListup = new ArrayList<ElevatorRequest>();
		ArrayList<ElevatorButtonMessage> elevatorRequestList  = request.getElevatorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorButtonMessage curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor > elevatorPosition){//???
					selectElevatorListup.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListup;
	}
	
	private ArrayList<FloorButtonMessage> getFloorDownRequestArray() {
		ArrayList<FloorButtonMessage> selectFloorListDown = new ArrayList<FloorButtonMessage>();
		ArrayList<FloorButtonMessage> floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			if(curFloorRequest.direction == requestDown && 
			   curFloorRequest.requestFloor < elevatorPosition){//???
				selectFloorListDown.add(curFloorRequest);
			}
		}
		
		return selectFloorListDown;
	}
	
	private ArrayList<ElevatorButtonMessage> getElevatorDownRequestArray() {
		ArrayList<ElevatorButtonMessage> selectElevatorListDown = new ArrayList<ElevatorButtonMessage>();
		ArrayList<ElevatorButtonMessage> elevatorButtonMessageList = request.getElevatorButtonMessageArray();
		
		for(int i = 0; i < elevatorButtonMessageList.size(); i++) {
			ElevatorButtonMessage curElevatorButtonMessage = elevatorButtonMessageList.get(i);
			
			if(curElevatorButtonMessage.requestFloor < elevatorPosition){//???
				selectElevatorListDown.add(curElevatorButtonMessage);
			}
		}
		
		return selectElevatorListDown;
	}
	
	private int getNearestUpRequest(ArrayList<FloorButtonMessage> seletFloorListup, ArrayList<ElevatorButtonMessage> selectElevatorListup) {
		int targetDistinationFromFloor    = 0;
		int targetDistinationFromElevator = 0;
		
		for(int i = 0 ; i < selectFloorListup.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int curRequestFloor = selectFloorListup.get(i).requestFloor;
			
			if(targetDistinationFromFloor == 0 || targetDistinationFromFloor > curRequestFloor){
				targetDistinationFromFloor = curRequestFloor;//???
			}
		}

		for(int i = 0; i < selectElevatorListup.size(); i++) {
			int curRequestFloor = selectElevatorListup.get(i).requestFloor;
			if(targetDistinationFromElevator == 0 || targetDistinationFromElevator > curRequestFloor) {
				targetDistinationFromElevator = curRequestFloor;//???
			}
		}
		
		if(targetDistinationFromFloor < targetDistinationFromElevator) {
			return targetDistinationFromFloor;
		}
		
		return targetDistinationFromElevator;
	}
	
	private int getNearestDownRequest(ArrayList<FloorButtonMessage> seletFloorListDown, ArrayList<ElevatorButtonMessage> selectElevatorListDown) {
		int targetDistinationFromFloor    = 0;
		int targetDistinationFromElevator = 0;
		
		for(int i = 0 ;i < selectFloorListDown.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int requestFloor = selectFloorListDown.get(i).requestFloor;
			
			if(targetDistinationFromFloor == 0 || targetDistinationFromFloor < requestFloor){
				targetDistinationFromFloor = requestFloor;//???
			}
		}

		for(int i = 0 ; i < selectElevatorListDown.size(); i++) {
			int requestFloor = selectElevatorListDown.get(i).requestFloor;
			
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
