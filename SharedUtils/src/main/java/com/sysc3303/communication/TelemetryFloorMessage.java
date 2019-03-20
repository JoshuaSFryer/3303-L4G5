package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TelemetryFloorMessage extends TelemetryMessage{
    @JsonCreator
    public TelemetryFloorMessage(@JsonProperty("secondTime") int secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
        super(secondTime, nanoSecondTime, OpCodes.TEL_FLOOR.getOpCode());
    }

    @Override
    public String toString(){
        return "TelemetryFloorMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime;
    }

    @Override
    public String summary(){
        return "Telemetry Floor-"+ secondTime + "s " + nanoSecondTime + "ns";
    }
}
