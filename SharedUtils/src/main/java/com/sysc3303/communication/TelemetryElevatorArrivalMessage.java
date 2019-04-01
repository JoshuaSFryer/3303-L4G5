package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class TelemetryElevatorArrivalMessage extends TelemetryElevatorPressArrivalMessage {

    @JsonCreator
	public TelemetryElevatorArrivalMessage(@JsonProperty("elevatorId") int elevatorId, @JsonProperty("floor") int floor,@JsonProperty("secondTime") long secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
		super(elevatorId, floor, secondTime, nanoSecondTime, OpCodes.TEL_ELEV_ARV.getOpCode());
	}
    @Override
    public String toString(){
        return "TelemetryElevatorArrivalMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime +
                "\t\nElevator Id:" + elevatorId +
                "\t\nFloor:" + floor;
    }
}
