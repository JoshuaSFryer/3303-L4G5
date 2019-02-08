package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.ElevatorStateMessage;
import com.sysc3303.communication.FloorArrivalMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.elevator.ElevatorVector;

/**
 * Handle the message from elevator
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class ElevatorRequestHandler implements Runnable {
	private Request             request;
	private Message             message;
	private FloorArrivalMessage floorArrivalMessage;
	private TargetFloorDecider  targetFloorDecider; 
	private GoToFloorMessageBox goToFloorMessageBox;
	
	/**
	 * elevator message handler constructor
	 * @para request
	 * @para message
	 */
	public ElevatorRequestHandler(Request request, Message message, GoToFloorMessageBox goToFloorMessageBox) {
		this.request             = request;
		this.message             = message;   
		this.goToFloorMessageBox = goToFloorMessageBox;
		targetFloorDecider = new TargetFloorDecider();
	}

	public void run() {
		System.out.println("ElevatorRequestHandler starting..");
		System.out.println("Current requests: ");
		System.out.println(request.toString());
		
		if(message instanceof ElevatorStateMessage) {
			ElevatorStateMessage message          = (ElevatorStateMessage)this.message;
			ElevatorVector       elevatorVector   = message.getElevatorVector();
			int                  destinationFloor = elevatorVector.targetFloor;
			int                  elevatorId       = message.getElevatorId();

			request.setElevatorVector(elevatorVector);
			
			System.out.println("Setting elevator vector: " + elevatorVector.toString());
			
			if(elevatorVector.currentFloor != destinationFloor) {
				return;
			}	
	
			removeTargetFloor(destinationFloor, elevatorVector.currentDirection);

			FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(destinationFloor, elevatorVector.currentDirection, elevatorId);
			
			setFloorArrivalMessage(floorArrivalMessage);
		}
		else if(message instanceof ElevatorButtonMessage) {
			ElevatorButtonMessage message = (ElevatorButtonMessage)this.message;
			
			request.addElevatorButtonMessage(message);
			
			int              targetFloor      = targetFloorDecider.decideTargetFloor(request);
			GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor);
			
			System.out.println("Decided target floor! : " + targetFloor);
			
			goToFloorMessageBox.setGoToFloorMessage(goToFloorMessage);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private synchronized void setFloorArrivalMessage(FloorArrivalMessage floorArrivalMessage) {
		this.floorArrivalMessage = floorArrivalMessage;
		notifyAll();
	}
	
	/**
	 * @return floorArrivalMessage
	 */
	public synchronized FloorArrivalMessage getFloorArrivalMessage() {
		waitUntilFloorArrivalMessageExists();
		
		FloorArrivalMessage floorArrivalMessage = this.floorArrivalMessage;
		
		this.floorArrivalMessage = null;
		
		notifyAll();
		
		return floorArrivalMessage;
	}
	
	private void waitUntilFloorArrivalMessageExists() {
		while(floorArrivalMessage == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This function is to update the floorRequestList and elevatorRequestList
	 * once the elevator arrived a target floor, the target floor needs to be removed potentially
	 * both from floorRequestList and elevatorRequestList.
	 * 
	 * @para targetFloor
	 * @para targetDirection
	 */
	private void removeTargetFloor(int targetFloor, Direction targetDirection) {
		System.out.println("Removing target Floor!!!");
		
		ArrayList<ElevatorButtonMessage> elevatorRequestList = request.getElevatorButtonMessageArray();
		ArrayList<FloorButtonMessage>    floorRequestList    = request.getFloorButtonMessageArray();
		
		for(int i = 0; i < elevatorRequestList.size(); i++) {
			if(elevatorRequestList.get(i).getDestinationFloor() == targetFloor) {
				elevatorRequestList.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < floorRequestList.size(); i++) {
			FloorButtonMessage curFloorRequest = floorRequestList.get(i);
			
			if(curFloorRequest.getFloor()     == targetFloor && 
			   curFloorRequest.getDirection() == targetDirection) {
				floorRequestList.remove(i);
				break;
			}
		}
		
		request.setElevatorButtonMessageArray(elevatorRequestList);
		request.setFloorButtonMessageArray(floorRequestList);
	}
}
