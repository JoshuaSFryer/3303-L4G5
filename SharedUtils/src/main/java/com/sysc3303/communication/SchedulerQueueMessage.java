package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchedulerQueueMessage extends Message {
	private String floorQueue;
	private String elevatorQueue;
	
	@JsonCreator
	public SchedulerQueueMessage(@JsonProperty("floorQueue") String floorQueue, @JsonProperty("elevatorQueue") String elevatorQueue) {
		super(OpCodes.SCHED_QUEUE.getOpCode());
		this.floorQueue    = floorQueue;
		this.elevatorQueue = elevatorQueue;
	}

	public String getFloorQueue() {
		return floorQueue;
	}

	public String getElevatorQueue() {
		return elevatorQueue;
	}
	
	public String toString() {
		return "SchedulerQueueMessage: \n" +
				floorQueue + "\n" +
				elevatorQueue;
	}
}
