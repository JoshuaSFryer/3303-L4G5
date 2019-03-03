package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Message sent when a button pressed within the elevator car.
 */
public class GUIElevatorCarMessage extends Message {

    public final int buttonNum;

    public GUIElevatorCarMessage(byte opcode, int buttonNum) {
        super(opcode);
        this.buttonNum = buttonNum;
    }
}
