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
		
		public Request() {
			NUMBER_OF_ELEVATOR  = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
			floorButtonMessages = new ArrayList<FloorButtonMessage>();
			initElevatorStatusArray();
		}
		
		public void printFloorButtonQueues() {
			String output = "FloorButtonQueues: [";
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				output += floorButtonMessages.get(i).getFloor();
				
				if(i != floorButtonMessages.size()-1) {
					output += ",";
				}
			}
			
			output += "]";
			System.out.println(output);
		}
		
		public void printElevatorButtonQueues() {
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
				output += "]\n";
			}
			
			System.out.println(output);
		}
		
		public synchronized void setElevatorIsStuck(int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorIsStuck();
		}
		
		public synchronized void setElevatorIsUnstuck(int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorIsUnstuck();
		}
		
		public boolean elevatorIsStuck(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).elevatorIsStuck();
		}
		
		public synchronized void setGeneratorOn() {
			generatorIsOn = true;
		}
		
		public synchronized void setGeneratorOff() {
			generatorIsOn = false;
		}
		
		public synchronized boolean generatorIsOn() {
			return generatorIsOn;
		}
		
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
		
		public void resetTargetDirection() {
			for(int i = 0; i < elevatorStatusArray.size(); i++) {
				ElevatorStatus curStatus = elevatorStatusArray.get(i);
				if(curStatus.getElevatorVector().currentDirection == Direction.IDLE) {
					curStatus.setTargetDirection(Direction.IDLE);
				}
			}
		}
		
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
		
		public void setTargetDirection(Direction direction, int elevatorId) {
			elevatorStatusArray.get(elevatorId).setTargetDirection(direction);
		}
		
		public boolean containsTargetFloorInElevatorButtonMessages(int elevatorId, int targetFloor) {
			ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr = elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr();
			
			for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
				if(elevatorButtonMessageArr.get(i).getDestinationFloor() == targetFloor) {
					return true;
				}
			}
			return false;
		}
		
		public Direction getTargetDirection(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).getTargetDirection();
		}
		
		private void initElevatorStatusArray() {
			elevatorStatusArray = new ArrayList<ElevatorStatus>();
			
			for(int i = 0; i < NUMBER_OF_ELEVATOR; i++) {
				elevatorStatusArray.add(new ElevatorStatus());
			}
		}
		
		public int getNumberOfElevator() {
			return NUMBER_OF_ELEVATOR;
		}

		public boolean hasSingleFloorButtonMessage() {
			boolean hasSingleFloorButtonMessage = false;
			
			if(floorButtonMessages.size() == 1) {
				hasSingleFloorButtonMessage = true;
			}
			return hasSingleFloorButtonMessage;
		}
		
		public boolean elevatorButtonMessagesIsEmpty(int elevatorId) {
			if(elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr().isEmpty()) {
				return true;
			}
			return false;
		}
		
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
		 * 
		 * @return ElevatorVector
		 */
		public synchronized ElevatorVector getElevatorVector(int elevatorId) {			
			return elevatorStatusArray.get(elevatorId).getElevatorVector();
		}

		/**
		 * @param elevatorVector
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
		 * @return ArrayList<ElevatorButtonMessage>
		 */
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArray(int elevatorId) {
			return elevatorStatusArray.get(elevatorId).getElevatorButtonMessageArr();
		}
		
		/**
		 * @param index
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
		 */
		public synchronized void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage, int elevatorId) {
			elevatorStatusArray.get(elevatorId).addElevatorButtonMessage(elevatorButtonMessage);
		}
		
		public synchronized void setElevatorButtonMessageArray(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArray, int elevatorId) {
			elevatorStatusArray.get(elevatorId).setElevatorButtonMessageArr(elevatorButtonMessageArray);
		}
		
		/**
		 * @param floorButtonMessageArr
		 */
		public synchronized void setFloorButtonMessageArray(ArrayList<FloorButtonMessage> floorButtonMessageArr) {
			floorButtonMessages = floorButtonMessageArr;
		}
		
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
		
		public boolean floorButtonMessagesIsEmpty() {
			if(floorButtonMessages.size() == 0) {
				return true;
			}
			return false;
		}
		
		public boolean floorButtonMessagesContains(int floor) {
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				int curFloor = floorButtonMessages.get(i).getFloor();
				if(floor == curFloor) {
					return true;
				}
			}
			
			return false;
		}
		
		public boolean elevatorButtonMessagesIsEmpty() {
			for(int i = 0; i < NUMBER_OF_ELEVATOR; i++) {
				if(!elevatorStatusArray.get(i).elevatorButtonMessageIsEmpty()) {
					return false;
				}
			}
			return true;
		}
}
