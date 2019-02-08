package com.sysc3303.communication;

import java.io.Serializable;

class ButtonPair {
    private boolean down;
    private boolean up;

    public ButtonPair(boolean downState, boolean upState) {
        this.down = downState;
        this.up = upState;
    }
}

public class GUIFloorMessage extends Message implements Serializable {
    private ButtonPair buttons;
    private int passengerFloor;
    public GUIFloorMessage(byte opcode, boolean downState, boolean upState, int passengerFloor) {
        super(opcode);
        this.buttons = new ButtonPair(downState, upState);
        this.passengerFloor = passengerFloor;
    }
    //TODO: toString()
}
