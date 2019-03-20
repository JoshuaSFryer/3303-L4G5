package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TelemetryArrivalMessage extends TelemetryMessage{
    @JsonCreator
    public TelemetryArrivalMessage(@JsonProperty("secondTime") int secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime) {
        super(secondTime, nanoSecondTime, OpCodes.TEL_ARRIVAL.getOpCode());
    }

    @Override
    public String toString(){
        return "TelemetryArrivalMessage\t\n" +
                "Seconds: " + getSecondTime() +
                "\t\nNanoseconds: " + nanoSecondTime;
    }

    @Override
    public String summary(){
        return "Telemetry Arrival-"+ secondTime + "s " + nanoSecondTime + "ns";
    }
}
