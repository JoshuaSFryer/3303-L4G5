package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message sent when a button pressed within the elevator car.
 */
public class GUIElevatorCarMessage extends Message {

    public final int buttonNum;

    @JsonCreator
    public GUIElevatorCarMessage(@JsonProperty("opcode") byte opcode, @JsonProperty("buttonNum") int buttonNum) {
        super(opcode);
        this.buttonNum = buttonNum;
    }
}
