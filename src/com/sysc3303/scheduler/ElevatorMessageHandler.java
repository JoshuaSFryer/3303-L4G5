package com.sysc3303.scheduler;

import java.net.InetAddress;

public class ElevatorMessageHandler extends Thread {
	private Request                request;
	private MessageHandler         messageHandler;
	private SchedulerElevatorStatus         elevatorStatus;
	private InetAddress            floorAddress;
	private int                    floorPort;
	private CommunicationHandler   communicationHandler;
	private ElevatorMessageHandler elevatorMessageHandler;
	private ElevatorVector         elevatorVector;
	private ElevatorStatus         elevatorStatus;
	
	public ElevatorMessageHandler(Request request, MessageHandler messageHandler, SchedulerElevatorStatus elevatorStatus,
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
	
	public ElevatorVector getElevatorVector() {
		return elevatorVector;
	}
	
	public void run() {
		boolean running = true;
		
		while(running) {
			if(messageHandler.recievedElevatorStateMessage) {
				
				elevatorVector       = messageHandler.getElevatorVector();
				int destinationFloor = elevatorVector.getDestinationFloor();
				
				request.addElevatorButtonMessage(messageHandler.getElevatorButtonMessage);
				request.setElevatorVector(elevatorVector);
				
				if(elevatorVector.currentFloor != destinationFloor) {
					continue;
				}	

				removeTargetFloor(destinationFloor, elevatorVector.direction);
				FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(destinationFloor, elevatorVector.direction);
				
				communicationHandler.send(floorArrivalMessage, floorAddress, floorPort);
				
				// dont forget to create message and send it to floor
			}
			/*else if(messageHandler.recievedElevatorRequestMessage) {
				request.addRecievedElevatorReqeustMessage(messageHandler.getElevatorRequestMessage());
			}*/
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
			ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray();
			ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
			
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
