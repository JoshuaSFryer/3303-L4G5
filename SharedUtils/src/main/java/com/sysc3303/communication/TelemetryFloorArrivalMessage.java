package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.commons.Direction;

public class TelemetryFloorArrivalMessage extends TelemetryFloorPressArrivalMessage {
	
	@JsonCreator
	public TelemetryFloorArrivalMessage(@JsonProperty("floor") int floor,@JsonProperty("direction") Direction direction, @JsonProperty("secondTime") long secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
		super(floor, direction, secondTime, nanoSecondTime, OpCodes.TEL_FLOOR_ARV.getOpCode());
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public String toString(){
        return "TelemetryFloorArrivalMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime +
                "\t\nFloor:" + floor +
                "\t\nDirection:" + direction;
    }
}
