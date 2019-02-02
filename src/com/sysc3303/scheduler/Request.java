package com.sysc3303.scheduler;

import java.util.ArrayList;

public class Request {
	//This list stores the raw data from floor, need to add <type>!!!!!!!!!!!!!!!!!!!!
		private ArrayList<FloorButtonMessage> floorRequestList;
		//This list stores the raw data from elevator,need to add <type>!!!!!!!!!!!!!!!!!!!!
		private ArrayList<ElevatonButtonMessage> elevatorRequestList;
		private ElevatorVector                elevatorVector;
		
		public synchronized ElevatorVector getElevatorVector() {
			return elevatorVector;
		}

		public synchronized setElevatorVector(ElevatorVector elevatorVector) {
			this.elevatorVector = elevatorVector;
		}

		public synchronized FloorButtonMessage getFloorButtonMessage(int index) {
			return floorRequestList.get(index);
		}
		
		public synchronized ArrayList<FloorButtonMessage> getFloorButtonMessageArray() {
			return floorRequestList;
		}
		
		public synchronized ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArray() {
			return elevatorRequestList;
		}
		
		public synchronized ElevatorButtonMessage getElevatorButtonMessage(int index) {
			return elevatorRequestList.get(index);
		}
		
		public synchronized void addFloorButtonMessage(FloorButtonMessage floorButtonMessage) {
			floorRequestList.add(floorButtonMessage);
		}
		
		public synchronized void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage) {
			elevatorRequestList.add(elevatorButtonMessage);
		}
		
}
