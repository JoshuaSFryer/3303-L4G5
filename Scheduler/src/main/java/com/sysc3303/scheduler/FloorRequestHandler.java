package com.sysc3303.scheduler;

import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.communication.GoToFloorMessage;

/**
 * @author Yu Yamanaka Xinrui Zhang
 * 
 * Handles message from floor
 */
public class FloorRequestHandler implements Runnable {
	private Request                 request;
	private FloorButtonMessage      message;
	private TargetFloorDecider      targetFloorDecider;
	private TargetFloorValidator    targetFloorValidator;
	private SchedulerMessageHandler schedulerMessageHandler;
	
	/**
	 * @param request
	 * @param message
	 */
	public FloorRequestHandler(Request request, FloorButtonMessage message, SchedulerMessageHandler schedulerMessageHandler) {
		this.request                 = request;
		this.message                 = message;
		this.targetFloorValidator    = new TargetFloorValidator();
		this.targetFloorDecider      = new TargetFloorDecider();
		this.schedulerMessageHandler = schedulerMessageHandler;
	}
	
	public void run() {
		System.out.println("FloorRequestHandler starting..");
		System.out.println("Current requests: ");
		System.out.println(request.toString());
		
		request.addFloorButtonMessage(message);
		
		int targetFloor = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request);
		
		if(targetFloor != -1) {
			GoToFloorMessage goToFloorMessage = new GoToFloorMessage(targetFloor, 0);
			System.out.println("Sending go to floor to elevator");
			System.out.println(goToFloorMessage.toString());
			schedulerMessageHandler.sendGoToFloor(goToFloorMessage);
		}
		/*
		if(targetFloorValidator.validTargetFloor(targetFloor, request.getElevatorVector())) {
			System.out.println("Sending go to floor to elevator");
			System.out.println(goToFloorMessage.toString());
			schedulerMessageHandler.sendGoToFloor(goToFloorMessage);
		}*/
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
