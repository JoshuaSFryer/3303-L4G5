package com.sysc3303.communication;

public class TelemetryElevatorPressArrivalMessage extends TelemetryMessage{
	protected int     floor;
	protected int     elevatorId;

	public TelemetryElevatorPressArrivalMessage(int elevatorId, int floor, long secondTime, long nanoSecondTime, byte opCode) {
		super(secondTime, nanoSecondTime, opCode);
		this.floor      = floor;
		this.elevatorId = elevatorId;
	}
	
	public int getFloor() {
		return floor;
	}

	public int getElevatorId() {
		return elevatorId;
	}
}
