package com.sysc3303.scheduler;

import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.communication.ElevatorButtonMessage;

/**
 * Keeps status of each elevator
 * @author Yu Yamanaka Xinrui Zhang
 *
 */
public class ElevatorStatus {
	private ElevatorVector                   elevatorVector;
	private ArrayList<ElevatorButtonMessage> elevatorButtonMessageArr;
	private Direction                        targetDirection;
	private boolean                          isStuck;
	private int                              stuckedTargetFloor;
	
	/**
	 * Elevator status constructor
	 */	
	public ElevatorStatus() {
		elevatorVector           = new ElevatorVector(0, Direction.IDLE, -1);
		elevatorButtonMessageArr = new ArrayList<ElevatorButtonMessage>(); 
		targetDirection          = Direction.IDLE;
		isStuck                  = false;
	}
	
	/**
	 * return true if the elevator is stuck
	 * false otherwise
	 * @return boolean
	 */
	public boolean elevatorIsStuck() {
		return isStuck;
	}
	
	/**
	 * sets elevator to stuck state
	 */	
	public void setElevatorIsStuck() {
		isStuck = true;
		stuckedTargetFloor = elevatorVector.targetFloor;
		elevatorVector = new ElevatorVector(elevatorVector.currentFloor, elevatorVector.currentDirection, -1);
	}
	
	/**
	 * sets elevator to unstuck state
	 */
	public void setElevatorIsUnstuck() {
		isStuck = false;
		elevatorVector = new ElevatorVector(elevatorVector.currentFloor, elevatorVector.currentDirection, stuckedTargetFloor);
	}
	
	/**
	 * return true if the elevator button message array is empty
	 * false otherwise
	 * @return boolean
	 */
	public boolean elevatorButtonMessageIsEmpty() {
		if(elevatorButtonMessageArr.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * sets target direction
	 * @param direction
	 */
	public void setTargetDirection(Direction direction) {
		this.targetDirection = direction;
	}
	
	/**
	 * gets elevator vector
	 * @return elevatorVector
	 */
	public ElevatorVector getElevatorVector() {
		return elevatorVector;
	}
	
	/**
	 * get target direction
	 * @return targetDirection
	 */
	public Direction getTargetDirection() {
		return targetDirection;
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
	 * @return elevatorButtonMessageArr
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
		
		output += elevatorVector.toString() + 
				  "\nTargetDirection: " + targetDirection +
				  "\nStuck: " + isStuck;
		return output;
	}
}
