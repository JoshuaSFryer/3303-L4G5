package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TelemetryElevatorMessage extends TelemetryMessage{

    @JsonCreator
    public TelemetryElevatorMessage(@JsonProperty("secondTime") int secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
        super(secondTime, nanoSecondTime, OpCodes.TEL_ELEVATOR.getOpCode());
    }

    @Override
    public String toString(){
        return "TelemetryElevatorMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime;
    }

    @Override
    public String summary(){
        return "Telemetry Elevator-"+ secondTime + "s " + nanoSecondTime + "ns";
    }
}


