package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TelemetryMessage extends Message{
    protected final long secondTime;
    protected final long nanoSecondTime;

    @JsonCreator
    public TelemetryMessage(@JsonProperty("secondTime") long secondTime, @JsonProperty("nanoSecondTime") long nanoSecondTime, byte opCode) {
        super(opCode);
        this.secondTime = secondTime;
        this.nanoSecondTime = nanoSecondTime;
    }

    public long getSecondTime(){
        return secondTime;
    }

    public long getNanoSecondTime() {
        return nanoSecondTime;
    }
}

