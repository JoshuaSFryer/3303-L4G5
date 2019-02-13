package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.ElevatorButtonMessage;

public class ElevatorStatus {
	ElevatorVector                   elevatorVector;
	ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr;
	
	public ElevatorStatus() {
		elevatorVector           = new ElevatorVector(0, Direction.IDLE, 0);
		elevatorButtonMessageArr = new ArrayList<ElevatorButtonMessage>(); 
	}
	
	public ElevatorVector getElevatorVector() {
		return elevatorVector;
	}
	
	public void setElevatorVector(ElevatorVector elevatorVector) {
		this.elevatorVector = elevatorVector;
	}
	
	public ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArr() {
		return elevatorButtonMessageArr;
	}
	
	public void setElevatorButtonMessageArr(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr) {
		this.elevatorButtonMessageArr = elevatorButtonMessageArr;
	}
	
	public void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage) {
		elevatorButtonMessageArr.add(elevatorButtonMessage);
	}
	
	public String toString() {
		String output = "\nElevator Button Message Array: \n\t";
		for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
			output += elevatorButtonMessageArr.get(i).toString();
		}
		
		output += elevatorVector.toString();
		
		return output;
	}
}
