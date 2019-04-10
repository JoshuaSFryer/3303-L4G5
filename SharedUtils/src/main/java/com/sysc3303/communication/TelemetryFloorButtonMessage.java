package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

public class TelemetryFloorButtonMessage extends TelemetryFloorPressArrivalMessage {

    @JsonCreator
	public TelemetryFloorButtonMessage(@JsonProperty("floor") int floor, @JsonProperty("direction") Direction direction, @JsonProperty("secondTime") long secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
		super(floor, direction, secondTime, nanoSecondTime, OpCodes.TEL_FLOOR_BTN.getOpCode());
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString(){
        return "TelemetryFloorButtonMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime +
                "\t\nFloor:" + floor +
                "\t\nDirection:" + direction;
    }
}
