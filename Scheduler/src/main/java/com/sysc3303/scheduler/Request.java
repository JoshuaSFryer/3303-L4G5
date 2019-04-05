package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.commons.ElevatorVector;

/**
 * Stores array of floor button messages 
 * and each elevators status
 * @author Yu Yamanaka Xinrui Zhang
 *
 */
public class Request {
		private ArrayList<FloorButtonMessage> floorButtonMessages;
		private ArrayList<ElevatorStatus>     elevatorStatusArray;
		private final int                     NUMBER_OF_ELEVATOR;
		private boolean                       generatorIsOn;
	
	       /**
	        * The Request's constructer
	        */
		public Request() {
			NUMBER_OF_ELEVATOR  = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
			floorButtonMessages = new ArrayList<FloorButtonMessage>();
			initElevatorStatusArray();
		}
		
		/**
		 * returns floor button queues
		 * @return
		 */
		public String getFloorBtnQueueStr() {
			String output = "FloorButtonQueues: [";
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				output += floorButtonMessages.get(i).getFloor();
				output += " " + floorButtonMessages.get(i).getDirection();
				
				if(i != floorButtonMessages.size()-1) {
					output += ",";
				}
			}
			output += "]";
			return output;
		}
		
		/**
		 * returns elevator button queues
		 * @return
		 */
		public String getElevatorBtnQueueStr() {
			String output = "ElevatorButtonQueues: \n";
			
			for(int i = 0; i < elevatorStatusArray.size(); i++) {
				ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr = elevatorStatusArray.get(i).getElevatorButtonMessageArr();
				output += "\t Elevator " + i + ": [";
				for(int j = 0; j < elevatorButtonMessageArr.size(); j++) {
					output += elevatorButtonMessageArr.get(j).getDestinationFloor();
					if(j != elevatorButtonMessageArr.size()-1) {
						output += ",";
					}
				}
				output += "]";
				output += " going to = " + elevatorStatusArray.get(i).getElevatorVector().targetFloor +
				          " stuck = " + elevatorStatusArray.get(i).elevatorIsStuck() + "\n";
			}
			return output;
		}
	
		/**
		 * Synchronized function to set the elevator with elevatorId to be stuck
		 * @param elevatorId
		 * @return
		 */
		public synchronized void setElevatorIsStuck(int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorIsStuck();
		}
		
		/**
		 * Synchronized function to set the elevator with elevatorId to be unstuck
		 * @param elevatorId
		 * @return
		 */
		public synchronized void setElevatorIsUnstuck(int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorIsUnstuck();
		}
				
		/**
		 * The function return true if the elevator with elevatorId is stuck,
		 * false otherwise.
		 * @param elevatorId
		 * @return boolean
		 */
		public boolean elevatorIsStuck(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).elevatorIsStuck();
		}
	
		/**
		 * Synchronized function to set the generator on
		 * @return
		 */
		public synchronized void setGeneratorOn() {
			generatorIsOn = true;
		}
	
		/**
		 * Synchronized function to set the generator off
		 * @return
		 */
		public synchronized void setGeneratorOff() {
			generatorIsOn = false;
		}
	
		/**
		 * The function return true if the generator is on,
		 * return false otherwise.
		 * @return boolean
		 */
		public synchronized boolean generatorIsOn() {
			return generatorIsOn;
		}
	
		/**
		 * The function return floor button message array
		 * @return ArrayList
		 */		
		public ArrayList<FloorButtonMessage> getFloorButtonUpMessageArray() {
			ArrayList<FloorButtonMessage> upMessage = new ArrayList<FloorButtonMessage>();
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				FloorButtonMessage curMessage = floorButtonMessages.get(i);
				if(curMessage.getDirection() == Direction.UP) {
					upMessage.add(curMessage);
				}
			}
			
			return upMessage;
		}
		
		/**
		 * The function resets target direction to be idle
		 * @return 
		 */		
		public void resetTargetDirection() {
			for(int i = 0; i < elevatorStatusArray.size(); i++) {
				ElevatorStatus curStatus = elevatorStatusArray.get(i);
				if(curStatus.getElevatorVector().currentDirection == Direction.IDLE) {
					curStatus.setTargetDirection(Direction.IDLE);
				}
			}
		}
	
		/**
		 * The function return floor button down message array
		 * @return ArrayList
		 */		
		public ArrayList<FloorButtonMessage> getFloorButtonDownMessageArray() {
			ArrayList<FloorButtonMessage> downMessage = new ArrayList<FloorButtonMessage>();
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				FloorButtonMessage curMessage = floorButtonMessages.get(i);
				if(curMessage.getDirection() == Direction.DOWN) {
					downMessage.add(curMessage);
				}
			}
			
			return downMessage;
		}
		
		/**
		 * The function set the target direction of elevator with elevatorId the direction given
		 * @param direction
		 * @param elevatorId
		 * @return 
		 */
		public void setTargetDirection(Direction direction, int elevatorId) {
			elevatorStatusArray.get(elevatorId).setTargetDirection(direction);
		}
	
		/**
		 * The function return true if the elevator button message array contains the targetfloor given
		 * return false otherwise.
		 * @return boolean
		 */		
		public boolean containsTargetFloorInElevatorButtonMessages(int elevatorId, int targetFloor) {
			ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr = elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr();
			
			for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
				if(elevatorButtonMessageArr.get(i).getDestinationFloor() == targetFloor) {
					return true;
				}
			}
			return false;
		}
	
		/**
		 * The function get the target floor direction of the elevator with elevatorId
		 * @param elevatorId
		 * @return Direction
		 */
		public Direction getTargetDirection(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).getTargetDirection();
		}
	
		/**
		 * The function initals elevator status array
		 * @return 
		 */
		private void initElevatorStatusArray() {
			elevatorStatusArray = new ArrayList<ElevatorStatus>();
			
			for(int i = 0; i < NUMBER_OF_ELEVATOR; i++) {
				elevatorStatusArray.add(new ElevatorStatus());
			}
		}
		
		/**
		 * The function return the number of elevator
		 * @return NUMBER_OF_ELEVATOR
		 */
		public int getNumberOfElevator() {
			return NUMBER_OF_ELEVATOR;
		}
	
		/**
		 * The function return true if the floor button message array has single
		 * floor button message, return false otherwise.
		 * @return boolean
		 */
		public boolean hasSingleFloorButtonMessage() {
			boolean hasSingleFloorButtonMessage = false;
			
			if(floorButtonMessages.size() == 1) {
				hasSingleFloorButtonMessage = true;
			}
			return hasSingleFloorButtonMessage;
		}
	
		/**
		 * The function return true if the elevator button message array is empty
		 * return false otherwise.
		 * @return boolean
		 */
		public boolean elevatorButtonMessagesIsEmpty(int elevatorId) {
			if(elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr().isEmpty()) {
				return true;
			}
			return false;
		}
	
		/**
		 * The function return true if all the elevators arrived
		 * return false otherwise.
		 * @return boolean
		 */
		public boolean everyElevatorArrived() {
			for(int i = 0; i < elevatorStatusArray.size(); i++) {
				ElevatorStatus elevatorStatus = elevatorStatusArray.get(i);
				ElevatorVector elevatorVector = elevatorStatus.getElevatorVector();
				if(elevatorStatus.elevatorIsStuck()) {
					continue;
				}
				if(elevatorVector.targetFloor != elevatorVector.currentFloor) {
					return false;
				}
				
			}
			return true;
		}
		
		/**
		 * @param elevatorId
		 * @return ElevatorVector
		 */
		public synchronized ElevatorVector getElevatorVector(int elevatorId) {			
			return elevatorStatusArray.get(elevatorId).getElevatorVector();
		}

		/**
		 * @param elevatorVector
		 * @param elevatorId
		 */
		public synchronized void setElevatorVector(ElevatorVector elevatorVector, int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorVector(elevatorVector);
		}

		/**
		 * @param index
		 * @return FloorButtonMessage
		 */
		public synchronized FloorButtonMessage getFloorButtonMessage(int index) {
			return floorButtonMessages.get(index);
		}
		
		/**
		 * @return ArrayList<FloorButtonMessage>
		 */
		public synchronized ArrayList<FloorButtonMessage> getFloorButtonMessageArray() {
			return floorButtonMessages;
		}
		
		/**
		 * @param elevatorId
		 * @return ArrayList<ElevatorButtonMessage>
		 */
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArray(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr();
		}
		
		/**
		 * @param index
		 * @param elevatorId
		 * @return ElevatorButtonMessage
		 */
		public synchronized ElevatorButtonMessage getElevatorButtonMessage(int elevatorId, int index) {
			return elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr().get(index);
		}
		
		/**
		 * 
		 * @param floorButtonMessage
		 */
		public synchronized void addFloorButtonMessage(FloorButtonMessage floorButtonMessage) {
			floorButtonMessages.add(floorButtonMessage);
		}
		
		/**
		 * @param elevatorButtonMessage
		 * @param elevatorId
		 */
		public synchronized void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage, int elevatorId) {
			elevatorStatusArray.get(elevatorId).addElevatorButtonMessage(elevatorButtonMessage);
		}
		
		/**
		 * @param elevatorButtonMessageArray
		 * @param elevatorId
		 */
		public synchronized void setElevatorButtonMessageArray(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArray, int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorButtonMessageArr(elevatorButtonMessageArray);
		}
		
		/**
		 * @param floorButtonMessageArr
		 */
		public synchronized void setFloorButtonMessageArray(ArrayList<FloorButtonMessage> floorButtonMessageArr) {
			floorButtonMessages = floorButtonMessageArr;
		}
		
		/**
		 * print the floor and elevator message array
		 * @return String
		 */
		public String toString() {
			String output = "\n=================\nfloorButtonMessages: \n";
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				output += floorButtonMessages.get(i).toString();
			}
			
			output += "\nelevatorStatusArray: \n\t";
			
			for(int i = 0; i < elevatorStatusArray.size(); i++) {
				output += elevatorStatusArray.get(i).toString();
			}
			
			output += "\n=================";
			
			return output;
		}
	
		/**
		 * return true if the floor message array is empty
		 * false otherwise
		 * @return boolean
		 */		
		public boolean floorButtonMessagesIsEmpty() {
			if(floorButtonMessages.size() == 0) {
				return true;
			}
			return false;
		}
	
		/**
		 * return true if the floor botton message array contains the floor number given
		 * false otherwise
		 * @return boolean
		 */			
		public boolean floorButtonMessagesContains(int floor) {
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				int curFloor = floorButtonMessages.get(i).getFloor();
				if(floor == curFloor) {
					return true;
				}
			}
			
			return false;
		}
	
		/**
		 * return true if the elevator message array is empty
		 * false otherwise
		 * @return boolean
		 */	
		public boolean elevatorButtonMessagesIsEmpty() {
			for(int i = 0; i < NUMBER_OF_ELEVATOR; i++) {
				if(!elevatorStatusArray.get(i).elevatorButtonMessageIsEmpty()) {
					return false;
				}
			}
			return true;
		}
}
