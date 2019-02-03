package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.ElevatorButtonMessage;
import com.sysc3303.commons.FloorButtonMessage;
import com.sysc3303.elevator.ElevatorVector;

/**
 * @author Yu Yamanaka Xinrui Zhang
 *
 */
public class Request {
		private ArrayList<FloorButtonMessage>    floorRequestList;
		private ArrayList<ElevatorButtonMessage> elevatorRequestList;
		private ElevatorVector                   elevatorVector;
		
		public Request() {
			floorRequestList    = new ArrayList<FloorButtonMessage>();
			elevatorRequestList = new ArrayList<ElevatorButtonMessage>();
			elevatorVector      = null;
		}
		
		/**
		 * 
		 * @return ElevatorVector
		 */
		public synchronized ElevatorVector getElevatorVector() {
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
			return floorRequestList.get(index);
		}
		
		/**
		 * @return ArrayList<FloorButtonMessage>
		 */
		public synchronized ArrayList<FloorButtonMessage> getFloorButtonMessageArray() {
			return floorRequestList;
		}
		
		/**
		 * @return ArrayList<ElevatorButtonMessage>
		 */
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArray() {
			return elevatorRequestList;
		}
		
		/**
		 * @param index
		 * @return ElevatorButtonMessage
		 */
		public synchronized ElevatorButtonMessage getElevatorButtonMessage(int index) {
			return elevatorRequestList.get(index);
		}
		
		/**
		 * 
		 * @param floorButtonMessage
		 */
		public synchronized void addFloorButtonMessage(FloorButtonMessage floorButtonMessage) {
			floorRequestList.add(floorButtonMessage);
		}
		
		/**
		 * @param elevatorButtonMessage
		 */
		public synchronized void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage) {
			elevatorRequestList.add(elevatorButtonMessage);
		}
		
		/**
		 * @param floorButtonMessageArr
		 */
		public synchronized void setFloorButtonMessageArray(ArrayList<FloorButtonMessage> floorButtonMessageArr) {
			floorRequestList = floorButtonMessageArr;
		}
		
		/**
		 * @param elevatorButtonMessageArr
		 */
		public synchronized void setElevatorButtonMessageArray(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr) {
			elevatorRequestList = elevatorButtonMessageArr;
		}	
}
