package com.sysc3303.scheduler;

import com.sysc3303.commons.Direction;
/**
 * This class stores target with direction.
 * @author Xinrui Zhang Yu Yamanaka
 *
 */
public class TargetWithDirection {
	private final int       targetFloor;
	private final Direction targetDirection;
	
	/**
    	* The TargetWithDirection class constructer
    	* @para targetFloor
    	* @para targetDirection
     	*/
	public TargetWithDirection(int targetFloor, Direction targetDirection) {
		this.targetFloor = targetFloor;
		this.targetDirection = targetDirection;
	}
	
	/**
    	* Get the current target floor
	* @return int
     	*/
	public int getTargetFloor() {
		return targetFloor;
	}
	
	/**
    	* Get the direction of current target floor
	* @return ditection
     	*/
	public Direction getTargetDirection() {
		return targetDirection;
	}
	
	/**
    	* Get the string of the target floor with direction 
	* @return String
     	*/
	public String toString() {
		return "\n TargetFloor: " + targetFloor + 
			   "\n TargetDirection: " + targetDirection;
	}
}
