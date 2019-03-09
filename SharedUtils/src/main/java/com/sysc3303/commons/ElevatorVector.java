package com.sysc3303.commons;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

@SuppressWarnings("serial")
public class ElevatorVector implements java.io.Serializable {
	
	public final int currentFloor;
	public final Direction currentDirection;
	public final int targetFloor;

	@JsonCreator
	public ElevatorVector(@JsonProperty("floor") int floor,@JsonProperty("dir") Direction dir,@JsonProperty("targetFloor") int targetFloor) {
		this.currentFloor = floor;
		this.currentDirection = dir;
		this.targetFloor = targetFloor;
	}

	public String toString() {
		return "Elevator Vector: \n\t\tcurrentFloor: " + currentFloor + 
			   " \n\t\tcurrentDirection:" + currentDirection + 
			   " \n\t\ttargetFloor: " + targetFloor;
	}
}
