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
public class Request_New {
		private ArrayList<FloorButtonMessage>    floorButtonMsgList;
		
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId0;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId1;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId2;
		private ArrayList<ElevatorButtonMessage> ButtonMsgListFromElevatorId3;
		
		private ArrayList<ElevatorVector> elevatorVectorList;
			
		public Request_New() {
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
		 * @return ArrayList<ElevatorVector>
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
		 * @param int
		 */
		public synchronized void setElevatorVectorOnIdx(int id, ElevatorVector elevatorVector) {
			elevatorVectorList.add(id, elevatorVector);
		}

		/**
		 * @param index
		 * @return FloorButtonMessage
		 */
		public synchronized FloorButtonMessage getFloorButtonMessage(int index) {
			return floorButtonMsgList.get(index);
		}
		
		/**
		 * @return ArrayList<FloorButtonMessage>
		 */
		public synchronized ArrayList<FloorButtonMessage> getFloorButtonMsgList() {
			return floorButtonMsgList;
		}
		
		/**
		 * @param elevatorIdx
		 * @return ArrayList<ElevatorButtonMessage>
		 */
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMsgList(int elevatorIdx) {
			switch elevatorIdx:
				case 0: 
					return ButtonMsgListFromElevatorId0; 
				case 1:
					return ButtonMsgListFromElevatorId1; 
				case 2:
					return ButtonMsgListFromElevatorId2; 
				case 3:
					return ButtonMsgListFromElevatorId3; 	
		}
		
		/**
		 * @param index
		 * @param elevatorIdx
		 * @return ElevatorButtonMessage
		 */
		public synchronized ElevatorButtonMessage getElevatorButtonMsgFromIdx(int elevatorIdx, int index) {
			switch elevatorIdx:
				case 0: 
					return ButtonMsgListFromElevatorId0.get(index); 
				case 1:
					return ButtonMsgListFromElevatorId1.get(index); 
				case 2:
					return ButtonMsgListFromElevatorId2.get(index); 
				case 3:
					return ButtonMsgListFromElevatorId3.get(index); 			
		}
		
		/**
		 * 
		 * @param floorButtonMessage
		 */
		public synchronized void addFloorButtonMessage(FloorButtonMessage floorButtonMessage) {
			floorButtonMsgList.add(floorButtonMessage);
		}
		
		/**
		 * @param elevatorIdx
		 * @param elevatorButtonMessage
		 */
		public synchronized void addElevatorButtonMsgToIdx(int elevatorIdx, ElevatorButtonMessage elevatorButtonMessage) {
			switch elevatorIdx:
				case 0: 
					 ButtonMsgListFromElevatorId0.add(elevatorButtonMessage); break;	
				case 1:
					 ButtonMsgListFromElevatorId1.add(elevatorButtonMessage); break;
				case 2: 
					 ButtonMsgListFromElevatorId2.add(elevatorButtonMessage); break;
				case 3:
					 ButtonMsgListFromElevatorId3.add(elevatorButtonMessage); break;
			
		}
		
		/**
		 * @param floorButtonMessageArr
		 */
		public synchronized void setFloorButtonMessageArray(ArrayList<FloorButtonMessage> floorButtonMessageArr) {
			floorButtonMsgList = floorButtonMessageArr;
		}
		
		/**
		 * @param elevatorButtonMessageArr
		 */
		public synchronized void setElevatorButtonMessageArray(int elevatorIdx, ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr) {
			switch elevatorIdx:
				case 0: 
					 ButtonMsgListFromElevatorId0 = elevatorButtonMessageArr; break;	
				case 1:
					 ButtonMsgListFromElevatorId1 = elevatorButtonMessageArr; break;
				case 2: 
					 ButtonMsgListFromElevatorId2 = elevatorButtonMessageArr; break;
				case 3:
					 ButtonMsgListFromElevatorId3 = elevatorButtonMessageArr; break;
		}	
	// did not change this toString function!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
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
