package com.sysc3303.scheduler;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

public class FloorMessageHandler extends Thread {
	private Request              request;
	private MessageHandler       messageHandler;
	private ElevatorStatus       elevatorStatus;
	private InetAddress          elevatorAddress;
	private int                  elevatorPort;
	private CommunicationHandler communicationHandler;
	private int                  targetFloor;
	
	public FloorMessageHandler(Request request, MessageHandler messageHandler, ElevatorStatus elevatorStatus, 
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
		int target = 0;
		if(elevatorStatus == ElevatorStatus.Stationary) {
			// want to set to null, this may need to be changed
			FloorRequest    floorRequest    = getEariestFloorRequest();
			ElevatorRequest elevatorRequest = getEariestElevatorRequest();
			
			//NOT SURE what the objects are called!!!!!!!!!!!!!!!!!!!!
			if(floorRequest.time.compareTo(elevatorRequest.time) > 0) {
				target = elevatorRequest.numberFromElevator;
			}
			else {
				target = floorRequest.numberFromFloor;
			}
		}
		else if(elevatorStatus == ElevatorStatus.GoingUp) {
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
	
	private FloorRequest getEariestFloorRequest() {
		FloorRequest floorRequest;
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			Date         curRequestTime  = curFloorRequest.time;
			
			if(floorRequest == null || floorRequest.time.compareTo(curRequestTime) > 0){
				floorRequest = curFloorRequest;
			}
		}
		
		return floorRequest;
	}
	
	private ElevatorRequest getEariestElevatorRequest() {
		ElevatorRequest elevatorRequest;
		//go through the elevatorRequestList to find the earliest request
		//not sure what time is being named in elevator????????????????????????
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			Date            curRequestTime     = curElevatorRequest.time;
			
			if(elevatorRequest == null || elevatorRequest.time.compareTo(curRequestTime) > 0) {
				elevatorRequest = curElevatorRequest;
			}
		}
		return elevatorRequest;
	}
	
	private ArrayList<FloorRequest> getFloorUpRequestArray() {
		ArrayList<FloorRequest> selectFloorListup = new ArrayList<FloorRequest>();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.direction == requestUp && 
			   curFloorRequest.requestFloor > elevatorPosition) {//???
				selectFloorListup.add(curFloorRequest);
			}
		}
		
		return selectFloorListup;
	}
	
	private ArrayList<ElevatorRequest> getElevatorUpRequestArray() {
		ArrayList<ElevatorRequest> selectElevatorListup = new ArrayList<ElevatorRequest>();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor > elevatorPosition){//???
					selectElevatorListup.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListup;
	}
	
	private ArrayList<FloorRequest> getFloorDownRequestArray() {
		ArrayList<FloorRequest> selectFloorListDown = new ArrayList<FloorRequest>();
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			//not sure what time is being named in floor????????????????????????
			FloorRequest curFloorRequest = floorRequestList.get(i);
			if(curFloorRequest.direction == requestDown && 
			   curFloorRequest.requestFloor < elevatorPosition){//???
				selectFloorListDown.add(curFloorRequest);
			}
		}
		
		return selectFloorListDown;
	}
	
	private ArrayList<ElevatorRequest> getElevatorDownRequestArray() {
		ArrayList<ElevatorRequest> selectElevatorListDown = new ArrayList<ElevatorRequest>();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			ElevatorRequest curElevatorRequest = elevatorRequestList.get(i);
			
			if(curElevatorRequest.requestFloor < elevatorPosition){//???
				selectElevatorListDown.add(curElevatorRequest);
			}
		}
		
		return selectElevatorListDown;
	}
	
	private int getNearestUpRequest(ArrayList<FloorRequest> seletFloorListup, ArrayList<ElevatorRequest> selectElevatorListup) {
		int targetFloor = 0;
		
		for(int i = 0 ; i < selectFloorListup.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int curRequestFloor = selectFloorListup.get(i).requestFloor;
			
			if(targetFloor == 0 || targetFloor > curRequestFloor){
				targetFloor = curRequestFloor;//???
			}
		}

		for(int i = 0; i < selectElevatorListup.size(); i++) {
			int curRequestFloor = selectElevatorListup.get(i).requestFloor;
			if(targetFloor == 0 || targetFloor > curRequestFloor) {
				targetFloor = curRequestFloor;//???
			}
		}
		return targetFloor;
	}
	
	private int getNearestDownRequest(ArrayList<FloorRequest> seletFloorListDown, ArrayList<ElevatorRequest> selectElevatorListDown) {
		int targetFloor = 0;
		for(int i = 0 ;i < selectFloorListDown.size();i++) {
			//not sure what time is being named in floor????????????????????????
			int requestFloor = selectFloorListDown.get(i).requestFloor;
			
			if(targetFloor == 0 || targetFloor < requestFloor){
				target = requestFloor;//???
			}
		}

		for(int i = 0 ; i < selectElevatorListDown.size(); i++) {
			int requestFloor = selectElevatorListDown.get(i).requestFloor;
			
			if(targetFloor == 0 || target < requestFloor){
				target = requestFloor;//???
			}
		}
		
		return targetFloor;
	}
}
