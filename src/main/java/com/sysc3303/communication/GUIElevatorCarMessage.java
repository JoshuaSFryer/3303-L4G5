package com.sysc3303.communication;
// Message for buttons pressed within the elevator car.
public class GUIElevatorCarMessage extends Message {

    private int buttonNum;

    public GUIElevatorCarMessage(byte opcode, int buttonNum) {
        super(opcode);
        this.buttonNum = buttonNum;
    }
}
