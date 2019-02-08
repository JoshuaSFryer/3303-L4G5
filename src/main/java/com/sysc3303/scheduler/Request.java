package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.communication.ElevatorButtonMessage;
import com.sysc3303.communication.FloorButtonMessage;
import com.sysc3303.elevator.ElevatorVector;

/**
 * @author Yu Yamanaka Xinrui Zhang
 *
 */
public class Request {
		private ArrayList<FloorButtonMessage>    floorButtonMsgList;
		
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId0;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId1;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId2;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId3;
		
		private ArrayList<ElevatorVector> elevatorVectorList;
			
		public Request() {
			floorButtonMsgList    		  = new ArrayList<FloorButtonMessage>();
			
			ButtonMsgListFromElevatorId0 = new ArrayList<ElevatorButtonMessage>();
			ButtonMsgListFromElevatorId1 = new ArrayList<ElevatorButtonMessage>();
			ButtonMsgListFromElevatorId2 = new ArrayList<ElevatorButtonMessage>();
			ButtonMsgListFromElevatorId3 = new ArrayList<ElevatorButtonMessage>();
			
			elevatorVectorList        	  = new ArrayList<ElevatorVector>();
		}
		
		public synchronized void waitUntilElevatorIdxArrives(int elevatorIdx) {
			while(elevatorVectorList.get(elevatorIdx).currentFloor != elevatorVectorList.get(elevatorIdx).targetFloor) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public boolean hasSingleFloorButtonMessage() {
			//waitUntilFloorButtonMessageExists();
			
			boolean hasSingleFloorButtonMessage = false;
			
			if(floorButtonMsgList.size() == 1) {
				hasSingleFloorButtonMessage = true;
			}
			
			//notifyAll();
			
			return hasSingleFloorButtonMessage;
			
		}
		
		public boolean ButtonMsgListFromElevatorIdxIsEmpty(int elevatorIdx) {
			switch elevatorIdx:
				case 0: 
					return ButtonMsgListFromElevatorId0.isEmpty(); 
				case 1:
					return ButtonMsgListFromElevatorId1.isEmpty(); 
				case 2:
					return ButtonMsgListFromElevatorId2.isEmpty(); 
				case 3:
					return ButtonMsgListFromElevatorId3.isEmpty(); 		
		}
//modified until here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		private void waitUntilFloorButtonMessageExists() {
			while(floorButtonMessages.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * 
		 * @return ElevatorVector
		 */
		public synchronized ArrayList<ElevatorVector> getElevatorVectorList() {
			while(elevatorVectorList.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			notifyAll();
			
			return elevatorVectorList;
		}

		/**
		 * @param elevatorVector
		 */
		public synchronized void addElevatorVector(ElevatorVector elevatorVector) {
			elevatorVectorList.add(elevatorVector);
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
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArray() {
			return elevatorButtonMessages;
		}
		
		/**
		 * @param index
		 * @return ElevatorButtonMessage
		 */
		public synchronized ElevatorButtonMessage getElevatorButtonMessage(int index) {
			return elevatorButtonMessages.get(index);
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
		public synchronized void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage) {
			elevatorButtonMessages.add(elevatorButtonMessage);
		}
		
		/**
		 * @param floorButtonMessageArr
		 */
		public synchronized void setFloorButtonMessageArray(ArrayList<FloorButtonMessage> floorButtonMessageArr) {
			floorButtonMessages = floorButtonMessageArr;
		}
		
		/**
		 * @param elevatorButtonMessageArr
		 */
		public synchronized void setElevatorButtonMessageArray(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr) {
			elevatorButtonMessages = elevatorButtonMessageArr;
		}	
		
		public String toString() {
			String output = "floorButtonMessages: %n";
			
			for(int i = 0; i < floorButtonMessages.size(); i++) {
				output += floorButtonMessages.get(i).toString();
			}
			
			output += "elevatorButtonMessages: %n";
			
			for(int i = 0; i < elevatorButtonMessages.size(); i++) {
				output += elevatorButtonMessages.get(i).toString();
			}
			
			output += "ElevatorVector: " + elevatorVector.toString();
			
			return output;
		}
}
