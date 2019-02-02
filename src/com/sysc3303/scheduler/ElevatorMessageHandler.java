package com.sysc3303.scheduler;

import java.net.InetAddress;

public class ElevatorMessageHandler extends Thread {
	private Request                request;
	private MessageHandler         messageHandler;
	private ElevatorStatus         elevatorStatus;
	private InetAddress            floorAddress;
	private int                    floorPort;
	private CommunicationHandler   communicationHandler;
	private ElevatorMessageHandler elevatorMessageHandler;
	
	public ElevatorMessageHandler(Request request, MessageHandler messageHandler, ElevatorStatus elevatorStatus,
			                      InetAddress floorAddress, int floorPort, CommunicationHandler communicationHandler, 
			                      ElevatorMessageHandler elevatorMessageHandler) {
		this.request                = request;
		this.messageHandler         = messageHandler;
		this.elevatorStatus         = elevatorStatus;
		this.floorAddress           = floorAddress;
		this.floorPort              = floorPort;
		this.communicationHandler   = communicationHandler;
		this.elevatorMessageHandler = elevatorMessageHandler;
	}
	
	public void run() {
		boolean running = true;
		
		while(running) {
			if(messageHandler.recievedElevatorStateMessage) {
				ElevatorVector elevatorVector = messageHandler.getElevatorVector();
				int            currentFloor   = elevatorVector.currentFloor;
				int            targetFloor    = elevatorMessageHandler.getTargetFloor();
				
				if(currentFloor != targetFloor) {
					continue;
				}	
				removeTargetFloor(targetFloor, elevatorVector.direction);
				// dont forget to create message and send it to floor
			}
			else if(messageHandler.recievedElevatorRequestMessage) {
				request.addRecievedElevatorReqeustMessage(messageHandler.getElevatorRequestMessage());
			}
			
		}
	}
	
	/**
	    * need another function which is to update the floorRequestList and elevatorRequestList
		* once the elevator arrived a target floor, the target floor needs to be removed potentially
		* both from floorRequestList and elevatorRequestList.
		* for elevatorRequestList: just scan and removed
		* for floorRequestList: need to scan the target floor with the direction
		*/
		private void removeTargetFloor(int targetFloor, Direction targetDirection) {
			ArrayList<> elevatorRequestList = request.getElevatorRequestList();
			ArrayList<> floorRequestList    = request.getFloorRequestList();
			
			for(int i = 0; i < elevatorRequestList.size(); i++) {
				if(elevatorRequestList.get(i).requestFloor == targetFloor) {
					elevatorRequestList.remove(i);
					break;
				}
			}
			
			for(int i = 0; i < floorRequestList.size(); i++) {
				FloorRequest curFloorRequest = floorRequestList.get(i);
				
				if(curFloorRequest.requestFloor == targetFloor && 
				   curFloorRequest.direction    == targetDirection) {
					floorRequestList.remove(i);
					break;
				}
			}
			
			request.setElevatorRequestList(elevatorRequestList);
			request.setFloorRequestList(floorRequestList);
		}
}
