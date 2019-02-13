package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.ElevatorButtonMessage;

/**
 * Keeps status of each elevator
 * @author Yu Yamanaka
 *
 */
public class ElevatorStatus {
	ElevatorVector                   elevatorVector;
	ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr;
	
	public ElevatorStatus() {
		elevatorVector           = new ElevatorVector(0, Direction.IDLE, 0);
		elevatorButtonMessageArr = new ArrayList<ElevatorButtonMessage>(); 
	}
	
	/**
	 * gets elevator vector
	 * @return
	 */
	public ElevatorVector getElevatorVector() {
		return elevatorVector;
	}
	
	/**
	 * sets elevator vector
	 * @param elevatorVector
	 */
	public void setElevatorVector(ElevatorVector elevatorVector) {
		this.elevatorVector = elevatorVector;
	}
	
	/**
	 * gets array of elevator button message
	 * @return
	 */
	public ArrayList<ElevatorButtonMessage> getElevatorButtonMessageArr() {
		return elevatorButtonMessageArr;
	}
	
	/**
	 * sets elevator buttton message array
	 * @param elevatorButtonMessageArr
	 */
	public void setElevatorButtonMessageArr(ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr) {
		this.elevatorButtonMessageArr = elevatorButtonMessageArr;
	}
	
	/**
	 * adds elevator button message
	 * @param elevatorButtonMessage
	 */
	public void addElevatorButtonMessage(ElevatorButtonMessage elevatorButtonMessage) {
		elevatorButtonMessageArr.add(elevatorButtonMessage);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = "\nElevator Button Message Array: \n\t";
		for(int i = 0; i < elevatorButtonMessageArr.size(); i++) {
			output += elevatorButtonMessageArr.get(i).toString();
		}
		
		output += elevatorVector.toString();
		
		return output;
	}
}
