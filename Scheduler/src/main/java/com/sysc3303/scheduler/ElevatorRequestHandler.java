package com.sysc3303.scheduler;




import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.ElevatorStateMessage;
import com.sysc3303.communication.FloorArrivalMessage;
import com.sysc3303.communication.GoToFloorMessage;
import com.sysc3303.communication.Message;
import com.sysc3303.communication.StuckMessage;
import com.sysc3303.communication.UnStuckMessage;
import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;

/**
 * Handle the message from elevator
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class ElevatorRequestHandler extends RequestHandler implements Runnable {
	
	/**
	 * elevator message handler constructor
	 * @para request
	 * @para message
	 */
	public ElevatorRequestHandler(Request request, Message message, SchedulerMessageHandler schedulerMessageHandler) {
		this.request                 = request;
		this.message                 = message;   
		this.schedulerMessageHandler = schedulerMessageHandler;
	}

	public void run() {
		log.info("ElevatorRequestHandler starting");
		
		if(message instanceof ElevatorStateMessage) {
			ElevatorStateMessage message          = (ElevatorStateMessage)this.message;
			ElevatorVector       elevatorVector   = message.getElevatorVector();
			int                  destinationFloor = elevatorVector.targetFloor;
			int                  elevatorId       = message.getElevatorId();
			int                  currentFloor     = elevatorVector.currentFloor;
			Direction            targetDirection  = request.getTargetDirection(elevatorId);
			
			if(!elevatorStateMessageValidator.isValidStateMessage(message, request)) {
				log.info("Recieved invalid state message: " + message);
				return;
			}
			
			request.setElevatorVector(elevatorVector, elevatorId);
			
			log.debug(request);
			
			if(currentFloor == destinationFloor) {
				log.info("Elevator " + elevatorId + " arrived at destination");
				
				removeTargetFloor(currentFloor, elevatorId, targetDirection);
				
				log.info("Removed target floor");
				log.info(request);
				
				ElevatorVector      elevatorVectorResetTargetFloor = new ElevatorVector(currentFloor, Direction.IDLE, -1);
				FloorArrivalMessage floorArrivalMessage            = new FloorArrivalMessage(destinationFloor, targetDirection, elevatorId);
				
				request.setElevatorVector(elevatorVectorResetTargetFloor, elevatorId);
				schedulerMessageHandler.sendFloorArrival(floorArrivalMessage);
			}	
			
			//generateAndSendGoToFloorMessage();
		}
		else if(message instanceof ElevatorButtonMessage) {
			ElevatorButtonMessage message     = (ElevatorButtonMessage)this.message;
			int                   elevatorId  = message.getElevatorId();
			int                   targetFloor = message.getDestinationFloor();
			ElevatorVector        curVector   = request.getElevatorVector(elevatorId);
			
			if(curVector.currentFloor != targetFloor) {
				sendGoToFloorMessageFromElevatorButtonMessage();
			}
		}
		else if(message instanceof StuckMessage) {
			StuckMessage message    = (StuckMessage)this.message;
			int          elevatorId = message.getElevatorId();

			request.setElevatorIsStuck(elevatorId);
		}
		else if(message instanceof UnStuckMessage) {
			UnStuckMessage message    = (UnStuckMessage)this.message;
			int            elevatorId = message.getElevatorId();
			
			request.setElevatorIsUnstuck(elevatorId);
		}
	}
	
	/**
	 * Generates and Sends GoToFloorMessage considering
	 * elevator button message and floor button message arrays 
	 * of particular elevator
	 */
	private void sendGoToFloorMessageFromElevatorButtonMessage() {
		ElevatorButtonMessage message        = (ElevatorButtonMessage)this.message;
		int                   elevatorId     = message.getElevatorId();
		
		request.addElevatorButtonMessage(message, elevatorId);
		
		log.info("Setting elevator button message");
		log.info(request);
		
		generateAndSendGoToFloorMessage();
	}
}
