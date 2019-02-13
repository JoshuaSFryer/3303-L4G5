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
		private ArrayList<FloorButtonMessage>    floorButtonMessages;
		private ArrayList<ElevatorButtonMessage> elevatorButtonMessages;
		private ElevatorVector                   elevatorVector;
		
		public Request() {
			floorButtonMessages    = new ArrayList<FloorButtonMessage>();
			elevatorButtonMessages = new ArrayList<ElevatorButtonMessage>();
			elevatorVector         = new ElevatorVector(0, Direction.IDLE, 0);
		}
		
		public synchronized void waitUntilElevatorArrives() {
			while(elevatorVector.currentFloor != elevatorVector.targetFloor) {
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
			
			if(floorButtonMessages.size() == 1) {
				hasSingleFloorButtonMessage = true;
			}
			
			//notifyAll();
			
			return hasSingleFloorButtonMessage;
			
		}
		
		public boolean elevatorButtonMessagesIsEmpty() {
			if(elevatorButtonMessages.isEmpty()) {
				return true;
			}
			return false;
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
		 * @return ElevatorVector
		 */
		public synchronized ElevatorVector getElevatorVector() {
			while(elevatorVector == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			notifyAll();
			
			return elevatorVector;
		}

		/**
		 * @param elevatorVector
		 */
		public synchronized void setElevatorVector(ElevatorVector elevatorVector) {
			this.elevatorVector = elevatorVector;
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
