package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.ConfigProperties;
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
		
		public Request() {
			NUMBER_OF_ELEVATOR  = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
			floorButtonMessages = new ArrayList<FloorButtonMessage>();
			initElevatorStatusArray();
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
}
