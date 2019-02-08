package com.sysc3303.scheduler;

import java.util.ArrayList;
import java.util.Date;

import com.sysc3303.commons.Direction;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.elevator.ElevatorVector;

/**
 * @author Yu Yamanaka Xinrui Zhang
 * 
 * Handles message from floor
 */
public class FloorRequestHandler implements Runnable {
	private Request             request;
	private FloorButtonMessage  message;
	private TargetFloorDecider  targetFloorDecider;
	private GoToFloorMessageBox goToFloorMessageBox;
	
	/**
	 * @param request
	 * @param message
	 */
	public FloorRequestHandler(Request request, FloorButtonMessage message, GoToFloorMessageBox goToFloorMessageBox) {
		this.request             = request;
		this.message             = message;
		this.goToFloorMessageBox = goToFloorMessageBox;
		targetFloorDecider       = new TargetFloorDecider();
	}
	
	public void run() {
		System.out.println("FloorRequestHandler starting..");
		System.out.println("Current requests: ");
		System.out.println(request.toString());
		
		int targetFloor;
		
		request.addFloorButtonMessage(message);
		
		if(request.hasSingleFloorButtonMessage() || request.elevatorButtonMessagesIsEmpty()) {
			targetFloor  = message.getFloor();
		}
		else {
			targetFloor = targetFloorDecider.decideTargetFloor(request);
		}
		
		GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor);
		
		goToFloorMessageBox.setGoToFloorMessage(goToFloorMessage);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
