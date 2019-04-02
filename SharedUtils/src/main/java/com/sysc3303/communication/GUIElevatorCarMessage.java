package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysc3303.communication.OpCodes;

/**
 * Message sent when a button pressed within the elevator car.
 */
public class GUIElevatorCarMessage extends Message {

    public final int buttonNum;

    @JsonCreator
    public GUIElevatorCarMessage(@JsonProperty("opcode") byte opcode, @JsonProperty("buttonNum") int buttonNum) {
        super(OpCodes.ELEVATOR_CLICK_SIMULATION.getOpCode());
        this.buttonNum = buttonNum;
    }
}
