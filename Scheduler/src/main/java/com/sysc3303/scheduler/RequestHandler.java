package com.sysc3303.scheduler;

import org.apache.log4j.Logger;

import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;

/**
 * Abstract class for request handlers
 * @author Yu Yamanaka
 *
 */
public abstract class RequestHandler {
	protected final int               INVALID_FLOOR = -1;
	protected Request                 request;
	protected SchedulerMessageHandler schedulerMessageHandler;
	protected Message                 message;
	protected TargetFloorDecider      targetFloorDecider = new TargetFloorDecider();
	protected Logger                  log                = Logger.getLogger(SchedulerMessageHandler.class);

	/**
	 * generates and sends goToFloorMessage
	 * to all valid elevators considering all
	 * elevator button press and floor requests
	 * @throws InterruptedException 
	 */
	protected void generateAndSendGoToFloorMessage() {
		while(!request.floorButtonMessagesIsEmpty()) {
			int[] targetFloorsFromFloorButtonMessages    = targetFloorDecider.selectTargetFloorFromFloorButtonMessages(request);
			int[] targetFloorsFromElevatorButtonMessages = targetFloorDecider.selectFloorFromAllElevatorsElevatorButtonMessage(request);
			int   numberOfElevator                       = request.getNumberOfElevator();
			
			for(int i = 0; i < numberOfElevator; i++) {
				int currentFloor = request.getElevatorVector(i).currentFloor;
				int targetFloor  = targetFloorDecider.getNearestFloor(targetFloorsFromFloorButtonMessages[i], targetFloorsFromElevatorButtonMessages[i], currentFloor);
				
				if(targetFloor != -1) {
					ElevatorVector curElevatorVector = request.getElevatorVector(i);
					ElevatorVector elevatorVector    = new ElevatorVector(curElevatorVector.currentFloor, curElevatorVector.currentDirection, targetFloor);
					request.setElevatorVector(elevatorVector, i);
					schedulerMessageHandler.sendGoToFloor(new GoToFloorMessage(targetFloor, i));
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
