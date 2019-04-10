package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

@SuppressWarnings("serial")
public class TelemetryFloorPressArrivalMessage extends TelemetryMessage {
	protected int       floor;
	protected Direction direction;
	
    @JsonCreator
	public TelemetryFloorPressArrivalMessage(@JsonProperty("floor") int floor, @JsonProperty("direction") Direction direction,@JsonProperty("secondTime") long secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime,@JsonProperty("opCodes") byte opCodes) {
    	super(secondTime, nanoSecondTime, opCodes);
    	this.floor           = floor;
        this.direction       = direction;
	}

	public int getFloor() {
		return floor;
	}

	public Direction getDirection() {
		return direction;
	}
}
